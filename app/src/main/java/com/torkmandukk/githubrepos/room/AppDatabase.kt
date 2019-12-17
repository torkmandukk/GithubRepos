package com.torkmandukk.githubrepos.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.torkmandukk.githubrepos.models.GitCommit
import com.torkmandukk.githubrepos.models.GithubUser
import com.torkmandukk.githubrepos.models.History
import com.torkmandukk.githubrepos.models.Repo

@Database(entities = [(History::class), (GithubUser::class), (Repo::class), (GitCommit::class)], version = 1, exportSchema = false)
@TypeConverters(Converter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun historyDao(): HistoryDao
    abstract fun githubUserDao(): GithubUserDao
    abstract fun repoDao(): RepoDao
    abstract fun commitDao(): CommitDao
}
