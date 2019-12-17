package com.torkmandukk.githubrepos.view.ui.commit

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.torkmandukk.githubrepos.models.FetchStatus
import com.torkmandukk.githubrepos.models.GitCommit
import com.torkmandukk.githubrepos.models.Resource
import com.torkmandukk.githubrepos.repository.CommitRepository
import com.torkmandukk.githubrepos.utils.AbsentLiveData
import timber.log.Timber
import javax.inject.Inject

class CommitActivityViewModel @Inject
constructor(private val repository: CommitRepository) : ViewModel() {

    private val repoName: MutableLiveData<String> = MutableLiveData()
    private val login: MutableLiveData<String> = MutableLiveData()
    private val page: MutableLiveData<Int> = MutableLiveData()
    val commitsLiveData: LiveData<Resource<List<GitCommit>>>

    var fetchStatus = FetchStatus()
        private set

    init {
        Timber.d("Injection CommitActivityViewModel")

        login.postValue(repository.getUserName())

        commitsLiveData = login.switchMap {
            login.value?.let { user ->
                repository.loadCommits(user, repoName.value.toString(), 0)
            }
                    ?: AbsentLiveData.create()
        }
    }

    fun setUser(user: String) {
        login.value = user
    }

    fun setRepo(repo: String) {
        repoName.value = repo
    }

    fun fetchStatus(resource: Resource<List<GitCommit>>) {
        fetchStatus = resource.fetchStatus
    }

    fun refresh(user: String) {
        fetchStatus = FetchStatus()
        login.value = user
    }

    fun postPage(page: Int) {
        this.page.value = page
    }
}
