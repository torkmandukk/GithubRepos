package com.torkmandukk.githubrepos.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.torkmandukk.githubrepos.models.GithubUser

@Dao
interface GithubUserDao {
  @Insert(onConflict = OnConflictStrategy.REPLACE)
  fun insertGithubUser(githubUser: GithubUser)

  @Query("SELECT * FROM GithubUser WHERE login = :user COLLATE NOCASE LIMIT 1")
  fun getGithubUser(user: String): LiveData<GithubUser>

  @Query("DELETE FROM GithubUser")
  fun truncateGithubUser()
}
