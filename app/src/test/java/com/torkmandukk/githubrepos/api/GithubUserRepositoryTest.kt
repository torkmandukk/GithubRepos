package com.torkmandukk.githubrepos.api

import android.content.Context
import android.content.SharedPreferences
import androidx.arch.core.executor.ArchTaskExecutor
import androidx.arch.core.executor.TaskExecutor
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyNoMoreInteractions
import com.nhaarman.mockitokotlin2.whenever
import com.torkmandukk.githubrepos.api.ApiResponseUtil.successCall
import com.torkmandukk.githubrepos.models.GithubUser
import com.torkmandukk.githubrepos.models.Resource
import com.torkmandukk.githubrepos.preference.PreferenceComponent_PrefAppComponent
import com.torkmandukk.githubrepos.repository.GithubUserRepository
import com.torkmandukk.githubrepos.room.GithubUserDao
import com.torkmandukk.githubrepos.room.RepoDao
import com.torkmandukk.githubrepos.utils.MockTestUtil
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class GithubUserRepositoryTest {

    private lateinit var repository: GithubUserRepository
    private val githubUserDao = mock<GithubUserDao>()
    private val repoDao = mock<RepoDao>()
    private val service = mock<GithubService>()

    private val user = "octocat"

    @Before
    fun init() {
        val context = mock<Context>()
        val preference = mock<SharedPreferences>()
        whenever(context.applicationContext).thenReturn(context)
        whenever(context.getSharedPreferences("UserProfile", Context.MODE_PRIVATE)).thenReturn(preference)
        PreferenceComponent_PrefAppComponent.init(context)

        repository = GithubUserRepository(githubUserDao, repoDao, service)

        ArchTaskExecutor.getInstance().setDelegate(object : TaskExecutor() {
            override fun executeOnDiskIO(runnable: Runnable) = runnable.run()
            override fun isMainThread() = true
            override fun postToMainThread(runnable: Runnable) = runnable.run()
        })
    }

    @After
    fun terminate() {
        ArchTaskExecutor.getInstance().setDelegate(null)
    }

    @Test
    fun loadUserFromNetwork() {
        val loadFromDB = MutableLiveData<GithubUser>()
        whenever(githubUserDao.getGithubUser(user)).thenReturn(loadFromDB)

        val mockResponse = MockTestUtil.mockGithubUser()
        val call = successCall(mockResponse)
        whenever(service.fetchGithubUser(user)).thenReturn(call)

        val data = repository.loadUser(user)
        verify(githubUserDao).getGithubUser(user)
        verifyNoMoreInteractions(service)

        val observer = mock<Observer<Resource<GithubUser>>>()
        data.observeForever(observer)
        verifyNoMoreInteractions(service)
        val updateData = MutableLiveData<GithubUser>()
        whenever(githubUserDao.getGithubUser(user)).thenReturn(updateData)

        loadFromDB.postValue(null)
        verify(observer).onChanged(Resource.loading(null, 1))
        verify(service).fetchGithubUser(user)
        verify(githubUserDao, times(2)).getGithubUser(user)

        updateData.postValue(mockResponse)
        verify(observer).onChanged(Resource.success(mockResponse, 2))
    }
}
