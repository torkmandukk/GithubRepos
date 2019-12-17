package com.torkmandukk.githubrepos.repository

import androidx.lifecycle.LiveData
import com.torkmandukk.githubrepos.api.ApiResponse
import com.torkmandukk.githubrepos.api.GithubService
import com.torkmandukk.githubrepos.models.Resource
import com.torkmandukk.githubrepos.models.GithubUser
import com.torkmandukk.githubrepos.models.Envelope
import com.torkmandukk.githubrepos.models.Repo
import com.torkmandukk.githubrepos.room.GithubUserDao
import com.torkmandukk.githubrepos.room.RepoDao
import com.skydoves.preferenceroom.InjectPreference
import com.torkmandukk.githubrepos.preference.PreferenceComponent_PrefAppComponent
import com.torkmandukk.githubrepos.preference.Preference_UserProfile
import org.jetbrains.anko.doAsync
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GithubUserRepository @Inject
constructor(
        private val githubUserDao: GithubUserDao,
        private val repoDao: RepoDao,
        private val service: GithubService
) {

    @InjectPreference
    lateinit var profile: Preference_UserProfile

    init {
        Timber.d("Injection GithubUserRepository")
        PreferenceComponent_PrefAppComponent.getInstance().inject(this)
    }

    fun refreshUser(user: String) {
        profile.putName(user)
        doAsync { githubUserDao.truncateGithubUser() }
    }

    fun loadUser(user: String): LiveData<Resource<GithubUser>> {
        return object : NetworkBoundRepository<GithubUser, GithubUser>() {
            override fun saveFetchData(items: GithubUser) {
                doAsync { githubUserDao.insertGithubUser(items) }
            }

            override fun shouldFetch(data: GithubUser?): Boolean {
                return data == null
            }

            override fun loadFromDb(): LiveData<GithubUser> {
                return githubUserDao.getGithubUser(user)
            }

            override fun fetchService(): LiveData<ApiResponse<GithubUser>> {
                return service.fetchGithubUser(user)
            }

            override fun onFetchFailed(envelope: Envelope?) {
                Timber.d("onFetchFailed : $envelope")
            }
        }.asLiveData()
    }

    fun loadRepos(user: String, page: Int): LiveData<Resource<List<Repo>>> {
        return object : NetworkBoundRepository<List<Repo>, List<Repo>>() {
            override fun saveFetchData(items: List<Repo>) {
                doAsync {
                    for (item in items) {
                        item.ownerName = user
                        item.page = page
                    }
                    repoDao.insertRepos(items)
                }
            }

            override fun shouldFetch(data: List<Repo>?): Boolean {
                return data == null || data.isEmpty()
            }

            override fun loadFromDb(): LiveData<List<Repo>> {
                return repoDao.getRepos(user, page)
            }

            override fun fetchService(): LiveData<ApiResponse<List<Repo>>> {
                return service.fetchRepos(user, page)
            }

            override fun onFetchFailed(envelope: Envelope?) {
                Timber.d("onFetchFailed : $envelope")
            }
        }.asLiveData()
    }

    fun getUserKeyName(): String = profile.nameKeyName()

    fun getPreferenceMenuPosition(): Int = profile.menuPosition

    fun getUserName(): String = profile.name!!

    fun putPreferenceMenuPosition(position: Int) = profile.putMenuPosition(position)

}
