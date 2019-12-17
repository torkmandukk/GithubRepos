package com.torkmandukk.githubrepos.view.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.torkmandukk.githubrepos.models.FetchStatus
import com.torkmandukk.githubrepos.models.Repo
import com.torkmandukk.githubrepos.models.GithubUser
import com.torkmandukk.githubrepos.models.Resource
import com.torkmandukk.githubrepos.repository.GithubUserRepository
import com.torkmandukk.githubrepos.utils.AbsentLiveData
import timber.log.Timber
import javax.inject.Inject

class MainActivityViewModel @Inject
constructor(
        private val githubUserRepository: GithubUserRepository
) : ViewModel() {

    private val login: MutableLiveData<String> = MutableLiveData()
    private val page: MutableLiveData<Int> = MutableLiveData()

    val githubUserLiveData: LiveData<Resource<GithubUser>>
    val reposLiveData: LiveData<Resource<List<Repo>>>

    var fetchStatus = FetchStatus()
        private set

    init {
        Timber.d("Injection MainActivityViewModel")

        login.postValue(getUserName())

        githubUserLiveData = login.switchMap {
            login.value?.let { user ->
                githubUserRepository.loadUser(user)
            }
                    ?: AbsentLiveData.create()
        }
        reposLiveData = page.switchMap {
            login.value?.let { user ->
                githubUserRepository.loadRepos(user, page.value!!)
            }
                    ?: AbsentLiveData.create()
        }
    }

    fun fetchStatus(resource: Resource<List<Repo>>) {
        fetchStatus = resource.fetchStatus
    }

    fun refresh(user: String) {
        fetchStatus = FetchStatus()
        login.value = user
        githubUserRepository.refreshUser(user)
    }

    fun postPage(page: Int) {
        this.page.value = page
    }

    fun getPreferenceMenuPosition() = githubUserRepository.getPreferenceMenuPosition()

    fun putPreferenceMenuPosition(position: Int) = githubUserRepository.putPreferenceMenuPosition(position)

    fun getUserName() = githubUserRepository.getUserName()

    fun getUserKeyName() = githubUserRepository.getUserKeyName()
}
