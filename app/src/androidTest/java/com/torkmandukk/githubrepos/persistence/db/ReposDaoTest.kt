package com.torkmandukk.githubrepos.persistence.db

import androidx.test.runner.AndroidJUnit4
import com.torkmandukk.githubrepos.models.Repo
import com.torkmandukk.githubrepos.utils.LiveDataTestUtil
import com.torkmandukk.githubrepos.utils.MockTestUtil
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ReposDaoTest : DBTest() {

    private lateinit var mockRepoList: MutableList<Repo>
    private lateinit var repo: Repo
    private val owner = "octocat"

    @Before
    fun initMock() {
        repo = MockTestUtil.mockRepo()
        mockRepoList = ArrayList()
        mockRepoList.add(repo)
        mockRepoList.add(repo)
        mockRepoList.add(repo)
    }

    @Test
    fun insertTest() {
        db.repoDao().insertRepos(mockRepoList)

        val loaded = LiveDataTestUtil.getValue(db.repoDao().getRepos(owner, 1))
        assertThat(loaded.size, `is`(3))
        assertThat(loaded[0].page, `is`(1))
    }
}
