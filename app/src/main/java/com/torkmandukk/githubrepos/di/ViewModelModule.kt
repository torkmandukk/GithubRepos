package com.torkmandukk.githubrepos.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.torkmandukk.githubrepos.factory.AppViewModelFactory
import com.torkmandukk.githubrepos.view.ui.commit.CommitActivityViewModel
import com.torkmandukk.githubrepos.view.ui.detail.DetailActivityViewModel
import com.torkmandukk.githubrepos.view.ui.main.MainActivityViewModel
import com.torkmandukk.githubrepos.view.ui.search.SearchActivityViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Suppress("unused")
@Module
internal abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(MainActivityViewModel::class)
    internal abstract fun bindMainActivityViewModel(mainActivityViewModel: MainActivityViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SearchActivityViewModel::class)
    internal abstract fun bindSearchActivityViewModel(searchActivityViewModel: SearchActivityViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(DetailActivityViewModel::class)
    internal abstract fun bindDetailActivityViewModel(detailActivityViewModel: DetailActivityViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CommitActivityViewModel::class)
    internal abstract fun bindCommitActivityViewModel(commitActivityViewModel: CommitActivityViewModel): ViewModel

    @Binds
    internal abstract fun bindViewModelFactory(appViewModelFactory: AppViewModelFactory): ViewModelProvider.Factory
}
