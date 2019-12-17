package com.torkmandukk.githubrepos.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.torkmandukk.githubrepos.room.Converter
import kotlinx.android.parcel.Parcelize

@Entity
@Parcelize
data class GitCommit(
        var sha: String,
        @PrimaryKey
        var node_id: String,
        var commit: CommitInfo,
        var url: String,
        var html_url: String,
        var comments_url: String,
        @TypeConverters(Converter::class)
        var parents: List<Parent>?,
        var owner: String,
        var repo: String,
        var page: Int
) : Parcelable

@Entity
@Parcelize
data class CommitInfo(
        @TypeConverters(Converter::class)
        var author: Author,
        @TypeConverters(Converter::class)
        var committer: Author,
        var message: String,
        @TypeConverters(Converter::class)
        var tree: Tree,
        @PrimaryKey
        var url: String,
        var comment_count: Int,
        @TypeConverters(Converter::class)
        var verification: Verification
) : Parcelable

@Entity
@Parcelize
data class Author(
        var name: String,
        @PrimaryKey
        var email: String,
        var date: String
) : Parcelable

@Entity
@Parcelize
data class Tree(
        var sha: String,
        @PrimaryKey
        var url: String
) : Parcelable

@Entity
@Parcelize
data class Verification(
        var verified: Boolean,
        var reason: String?,
        var signature: String?,
        var payload: String?
) : Parcelable

@Entity
@Parcelize
data class Parent(
        @PrimaryKey
        var sha: String,
        var url: String,
        var html_url: String
) : Parcelable