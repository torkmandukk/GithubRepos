package com.torkmandukk.githubrepos.repository

import androidx.lifecycle.LiveData
import com.torkmandukk.githubrepos.api.ApiResponse
import com.torkmandukk.githubrepos.api.GithubService
import com.torkmandukk.githubrepos.models.Envelope
import com.torkmandukk.githubrepos.models.GitCommit
import com.torkmandukk.githubrepos.models.Resource
import com.torkmandukk.githubrepos.room.CommitDao
import com.skydoves.preferenceroom.InjectPreference
import com.torkmandukk.githubrepos.preference.PreferenceComponent_PrefAppComponent
import com.torkmandukk.githubrepos.preference.Preference_UserProfile
import org.jetbrains.anko.doAsync
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CommitRepository @Inject
constructor(
        private val commitDao: CommitDao,
        private val service: GithubService
) {
    @InjectPreference
    lateinit var profile: Preference_UserProfile

    init {
        Timber.d("Injection CommitRepository")
        PreferenceComponent_PrefAppComponent.getInstance().inject(this)
    }

    fun loadCommits(user: String, repo: String, page: Int): LiveData<Resource<List<GitCommit>>> {
        return object : NetworkBoundRepository<List<GitCommit>, List<GitCommit>>() {
            override fun saveFetchData(items: List<GitCommit>) {
                doAsync {
                    for (item in items) {
                        item.owner = user
                        item.repo = repo
                        item.page = page
                    }
                    commitDao.insertCommits(items)
                }
            }

            override fun shouldFetch(data: List<GitCommit>?): Boolean {
                return data == null || data.isEmpty()
            }

            override fun loadFromDb(): LiveData<List<GitCommit>> {
                return commitDao.getCommits(user, repo, page)
            }

            override fun fetchService(): LiveData<ApiResponse<List<GitCommit>>> {
                return service.fetchCommits(user, repo, page)
            }

            override fun onFetchFailed(envelope: Envelope?) {
                Timber.d("onFetchFailed : $envelope")
            }
        }.asLiveData()
    }

    fun getUserName(): String = profile.name!!
}
