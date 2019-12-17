package com.torkmandukk.githubrepos.room

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.torkmandukk.githubrepos.models.*

class Converter {
    private val gson = Gson()
    @TypeConverter
    fun toGithubUser(data: String?): GithubUser {
        val listType = object : TypeToken<GithubUser?>() {}.type
        return gson.fromJson(data, listType)
    }

    @TypeConverter
    fun fromGithubUser(owner: GithubUser?): String {
        val type = object : TypeToken<GithubUser>() {}.type
        return gson.toJson(owner, type)
    }

    @TypeConverter
    fun toCommitInfo(data: String?): CommitInfo {
        val listType = object : TypeToken<CommitInfo?>() {}.type
        return gson.fromJson(data, listType)
    }

    @TypeConverter
    fun fromCommitInfo(owner: CommitInfo?): String {
        val type = object : TypeToken<CommitInfo>() {}.type
        return gson.toJson(owner, type)
    }

    @TypeConverter
    fun toAuthor(data: String?): Author {
        val listType = object : TypeToken<Author?>() {}.type
        return gson.fromJson(data, listType)
    }

    @TypeConverter
    fun fromAuthor(owner: Author?): String {
        val type = object : TypeToken<Author>() {}.type
        return gson.toJson(owner, type)
    }

    @TypeConverter
    fun toTree(data: String?): Tree {
        val listType = object : TypeToken<Tree?>() {}.type
        return gson.fromJson(data, listType)
    }

    @TypeConverter
    fun fromTree(owner: Tree?): String {
        val type = object : TypeToken<Tree>() {}.type
        return gson.toJson(owner, type)
    }

    @TypeConverter
    fun toVerification(data: String?): Verification {
        val listType = object : TypeToken<Verification?>() {}.type
        return gson.fromJson(data, listType)
    }

    @TypeConverter
    fun fromVerification(owner: Verification?): String {
        val type = object : TypeToken<Verification>() {}.type
        return gson.toJson(owner, type)
    }

    @TypeConverter
    fun toParents(data: String?): List<Parent> {
        val listType = object : TypeToken<List<Parent>?>() {}.type
        return gson.fromJson(data, listType)
    }

    @TypeConverter
    fun fromParents(owner: List<Parent>?): String {
        val type = object : TypeToken<List<Parent>>() {}.type
        return gson.toJson(owner, type)
    }
}