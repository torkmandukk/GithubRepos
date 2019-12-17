package com.torkmandukk.githubrepos.preference

import com.torkmandukk.githubrepos.repository.CommitRepository
import com.torkmandukk.githubrepos.repository.GithubUserRepository
import com.torkmandukk.githubrepos.view.ui.detail.DetailActivityViewModel
import com.torkmandukk.githubrepos.view.ui.search.SearchActivityViewModel
import com.skydoves.preferenceroom.PreferenceComponent

@PreferenceComponent(entities = [Profile::class])
interface PrefAppComponent {
    fun inject(target: SearchActivityViewModel)
    fun inject(target: DetailActivityViewModel)
    fun inject(target: GithubUserRepository)
    fun inject(target: CommitRepository)
}
