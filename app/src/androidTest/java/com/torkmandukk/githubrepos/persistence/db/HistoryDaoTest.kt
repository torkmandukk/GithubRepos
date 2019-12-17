package com.torkmandukk.githubrepos.persistence.db

import androidx.test.runner.AndroidJUnit4
import com.torkmandukk.githubrepos.models.History
import com.torkmandukk.githubrepos.utils.LiveDataTestUtil
import com.torkmandukk.githubrepos.utils.MockTestUtil
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.notNullValue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class HistoryDaoTest : DBTest() {

    private lateinit var history: History
    private val search = "octocat"

    @Before
    fun initMock() {
        history = MockTestUtil.mockHistory()
    }

    @Test
    fun insertHistoryTest() {
        db.historyDao().insertHistory(history)

        val loaded = LiveDataTestUtil.getValue(db.historyDao().selectRecentHistoryList())
        assertThat(loaded, notNullValue())
        assertThat(loaded[0].search, `is`(search))
        assertThat(loaded[0].history, `is`(MockTestUtil.mockTime))
    }

    @Test
    fun deleteHistoryTest() {
        db.historyDao().insertHistory(history)
        db.historyDao().deleteHistory(search)

        val loaded = LiveDataTestUtil.getValue(db.historyDao().selectRecentHistoryList())
        assertThat(loaded.size, `is`(0))
    }
}
