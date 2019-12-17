package com.torkmandukk.githubrepos.repository

import androidx.lifecycle.LiveData
import com.torkmandukk.githubrepos.models.History
import com.torkmandukk.githubrepos.room.HistoryDao
import org.jetbrains.anko.doAsync
import timber.log.Timber
import java.util.Calendar
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HistoryRepository @Inject
constructor(private val historyDao: HistoryDao) {

    init {
        Timber.d("Injection HistoryRepository")
    }

    fun insertHistory(search: String) {
        doAsync {
            historyDao.insertHistory(History(search, Calendar.getInstance().timeInMillis))
            Timber.d("Dao insert history : $search")
        }
    }

    fun selectHistories(): LiveData<List<History>> {
        return historyDao.selectRecentHistoryList()
    }

    fun deleteHistory(history: History) {
        doAsync {
            historyDao.deleteHistory(history.search)
            Timber.d("Dao delete history : ${history.search}")
        }
    }
}
