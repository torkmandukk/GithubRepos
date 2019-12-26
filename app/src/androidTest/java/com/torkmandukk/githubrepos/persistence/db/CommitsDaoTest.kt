package com.torkmandukk.githubrepos.persistence.db

import androidx.test.runner.AndroidJUnit4
import com.torkmandukk.githubrepos.models.GitCommit
import com.torkmandukk.githubrepos.utils.LiveDataTestUtil
import com.torkmandukk.githubrepos.utils.MockTestUtil
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CommitsDaoTest : DBTest() {
    private lateinit var mockCommitList: MutableList<GitCommit>
    private lateinit var commit: GitCommit
    private val owner = "octocat"
    private val repo = "git-consortium"

    @Before
    fun initMock() {
        commit = MockTestUtil.mockCommit()
        mockCommitList = ArrayList()
        mockCommitList.add(commit)
        mockCommitList.add(commit)
        mockCommitList.add(commit)
    }

    @Test
    fun insertTest() {
        db.commitDao().insertCommits(mockCommitList)

        val loaded = LiveDataTestUtil.getValue(db.commitDao().getCommits(owner, repo, 1))
        MatcherAssert.assertThat(loaded.size, CoreMatchers.`is`(3))
        MatcherAssert.assertThat(loaded[0].page, CoreMatchers.`is`(1))
    }
}