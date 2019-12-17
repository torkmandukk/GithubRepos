package com.torkmandukk.githubrepos

import com.facebook.stetho.Stetho
import com.torkmandukk.githubrepos.di.DaggerAppComponent
import com.torkmandukk.githubrepos.preference.PreferenceComponent_PrefAppComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import timber.log.Timber

@Suppress("unused")
class GithubReposApplication : DaggerApplication() {

    private val appComponent = DaggerAppComponent.builder()
            .application(this)
            .build()

    override fun onCreate() {
        super.onCreate()
        appComponent.inject(this)

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        PreferenceComponent_PrefAppComponent.init(this)
        Stetho.initializeWithDefaults(this)
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return appComponent
    }
}
