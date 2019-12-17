package com.torkmandukk.githubrepos.view.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.torkmandukk.githubrepos.models.GithubUser
import com.torkmandukk.githubrepos.models.Resource
import com.torkmandukk.githubrepos.repository.GithubUserRepository
import com.torkmandukk.githubrepos.utils.AbsentLiveData
import timber.log.Timber
import javax.inject.Inject

class DetailActivityViewModel @Inject
constructor(private val repository: GithubUserRepository) : ViewModel() {

    private val login: MutableLiveData<String> = MutableLiveData()
    val githubUserLiveData: LiveData<Resource<GithubUser>>

    init {
        Timber.d("Injection DetailActivityViewModel")

        githubUserLiveData = login.switchMap {
            login.value?.let { user -> repository.loadUser(user) }
                    ?: AbsentLiveData.create()
        }
    }

    fun setUser(user: String) {
        login.value = user
    }

    fun getUserKeyName(): String = repository.getUserKeyName()
}
