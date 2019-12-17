package com.torkmandukk.githubrepos

import android.app.Application
import com.torkmandukk.githubrepos.preference.PreferenceComponent_PrefAppComponent

/**
 * We use a separate App for tests to prevent initializing dependency injection.
 *
 * See [com.torkmandukk.githubrepos.util.JunitTestRunner].
 */
class TestGithubReposApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        PreferenceComponent_PrefAppComponent.init(this)
    }
}
