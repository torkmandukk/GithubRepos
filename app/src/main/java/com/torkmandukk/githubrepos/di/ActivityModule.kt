package com.torkmandukk.githubrepos.di

import com.torkmandukk.githubrepos.view.ui.commit.CommitActivity
import com.torkmandukk.githubrepos.view.ui.detail.DetailActivity
import com.torkmandukk.githubrepos.view.ui.main.MainActivity
import com.torkmandukk.githubrepos.view.ui.search.SearchActivity

import dagger.Module
import dagger.android.ContributesAndroidInjector

@Suppress("unused")
@Module
abstract class ActivityModule {

    @ContributesAndroidInjector
    internal abstract fun contributeMainActivity(): MainActivity

    @ContributesAndroidInjector
    internal abstract fun contributeSearchActivity(): SearchActivity

    @ContributesAndroidInjector
    internal abstract fun contributeDetailActivity(): DetailActivity

    @ContributesAndroidInjector
    internal abstract fun contributeCommitActivity(): CommitActivity
}
