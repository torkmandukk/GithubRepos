package com.torkmandukk.githubrepos.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.torkmandukk.githubrepos.models.History

@Dao
interface HistoryDao {
    @Query("SELECT* FROM SearchHistory ORDER BY history DESC LIMIT 20")
    fun selectRecentHistoryList(): LiveData<List<History>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertHistory(history: History)

    @Query("DELETE FROM SearchHistory WHERE search = :search")
    fun deleteHistory(search: String)
}
