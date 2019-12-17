package com.torkmandukk.githubrepos.persistence.db

import androidx.test.runner.AndroidJUnit4
import com.torkmandukk.githubrepos.models.GithubUser
import com.torkmandukk.githubrepos.utils.LiveDataTestUtil
import com.torkmandukk.githubrepos.utils.MockTestUtil
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.notNullValue
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class GithubUserDaoTest : DBTest() {

    private lateinit var githubUser: GithubUser
    private val login = "octocat"

    @Before
    fun initMock() {
        githubUser = MockTestUtil.mockGithubUser()
    }

    @Test
    fun insertGithubUserTest() {
        db.githubUserDao().insertGithubUser(githubUser)

        val loaded = LiveDataTestUtil.getValue(db.githubUserDao().getGithubUser(login))
        assertThat(loaded, notNullValue())
        assertThat(loaded.login, `is`(login))
    }

    @Test
    fun truncateGithubUserTable() {
        db.githubUserDao().truncateGithubUser()

        val loaded = LiveDataTestUtil.getValue(db.githubUserDao().getGithubUser(login))
        Assert.assertNull(loaded)
    }
}
