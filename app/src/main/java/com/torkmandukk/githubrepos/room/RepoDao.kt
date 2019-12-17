package com.torkmandukk.githubrepos.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.torkmandukk.githubrepos.models.Repo

@Dao
interface RepoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRepos(repos: List<Repo>)

    @Query("SELECT * FROM Repo WHERE ownerName = :owner_ AND page = :page_")
    fun getRepos(owner_: String, page_: Int): LiveData<List<Repo>>
}