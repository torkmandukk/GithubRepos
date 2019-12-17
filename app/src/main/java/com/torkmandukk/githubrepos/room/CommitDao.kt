package com.torkmandukk.githubrepos.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.torkmandukk.githubrepos.models.GitCommit

@Dao
interface CommitDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCommits(gitCommits: List<GitCommit>)

    @Query("SELECT * FROM GitCommit WHERE owner = :owner_ AND repo = :repo_ AND page = :page_")
    fun getCommits(owner_: String, repo_: String, page_: Int): LiveData<List<GitCommit>>
}