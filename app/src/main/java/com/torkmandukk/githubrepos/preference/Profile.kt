package com.torkmandukk.githubrepos.preference

import com.skydoves.preferenceroom.KeyName
import com.skydoves.preferenceroom.PreferenceEntity

@Suppress("unused")
@PreferenceEntity("UserProfile")
open class Profile {

    @KeyName("name")
    @JvmField
    val userName = "octocat"

    @KeyName("menuPosition")
    @JvmField
    val selectedPosition = 0
}
