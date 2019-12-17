package com.torkmandukk.githubrepos.models

import androidx.room.Entity
import java.io.Serializable

@Entity(primaryKeys = [("login")])
data class GithubUser(
        var login: String,
        val id: Int,
        val avatar_url: String,
        val gravatar_id: String,
        val url: String,
        val html_url: String,
        val followers_url: String,
        val following_url: String,
        val gists_url: String,
        val starred_url: String,
        val subscriptions_url: String,
        val organizations_url: String,
        val repos_url: String,
        val events_url: String,
        val received_events_url: String,
        val type: String,
        val site_admin: Boolean,
        var name: String?,
        val company: String?,
        val blog: String?,
        val location: String?,
        val email: String?,
        val hireable: Boolean?,
        val bio: String?,
        val public_repos: Int,
        val public_gists: Int,
        val followers: Int,
        val following: Int,
        val created_at: String,
        val updated_at: String
) : Serializable {
    constructor() : this("", 0, "", "", "", "", "", "", "", "", "", "", "", "", "", "", false, "", "", "", "", "", false, "", 0, 0, 0, 0, "", "")
}
