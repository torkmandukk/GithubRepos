package com.torkmandukk.githubrepos.persistence.preference

import android.content.Context
import androidx.test.InstrumentationRegistry
import org.junit.Before

abstract class PreferenceTest {

  lateinit var context: Context

  @Before
  fun initPreference() {
    context = InstrumentationRegistry.getTargetContext()
  }
}
