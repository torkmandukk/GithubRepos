package com.torkmandukk.githubrepos.view.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.torkmandukk.githubrepos.models.GithubUser
import com.torkmandukk.githubrepos.models.History
import com.torkmandukk.githubrepos.models.Resource
import com.torkmandukk.githubrepos.repository.GithubUserRepository
import com.torkmandukk.githubrepos.repository.HistoryRepository
import com.torkmandukk.githubrepos.utils.AbsentLiveData
import timber.log.Timber
import javax.inject.Inject

class SearchActivityViewModel @Inject
constructor(
  private val githubUserRepository: GithubUserRepository,
  private val historyRepository: HistoryRepository
) : ViewModel() {

  val login: MutableLiveData<String> = MutableLiveData()
  val githubUserLiveData: LiveData<Resource<GithubUser>>
  val historyLiveData: LiveData<List<History>>

  init {
    Timber.d("Injection SearchActivityViewModel")

    historyLiveData = historyRepository.selectHistories()

    githubUserLiveData = login.switchMap { user ->
      login.value?.let { githubUserRepository.loadUser(user) }
        ?: AbsentLiveData.create()
    }
  }

  fun insertHistory(search: String) = historyRepository.insertHistory(search)

  fun deleteHistory(history: History) = historyRepository.deleteHistory(history)

  fun getPreferenceUserKeyName(): String = githubUserRepository.getUserKeyName()
}
