package com.torkmandukk.githubrepos.api

import androidx.arch.core.executor.ArchTaskExecutor
import androidx.arch.core.executor.TaskExecutor
import com.torkmandukk.githubrepos.utils.LiveDataTestUtil
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import okio.Okio
import okio.buffer
import okio.source
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.notNullValue
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.nio.charset.StandardCharsets

@RunWith(JUnit4::class)
class GithubServiceTest {
    private lateinit var service: GithubService
    private lateinit var mockWebServer: MockWebServer

    @Throws(IOException::class)
    @Before
    fun createService() {
        mockWebServer = MockWebServer()
        service = Retrofit.Builder()
                .baseUrl(mockWebServer.url("/"))
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(LiveDataCallAdapterFactory())
                .build()
                .create(GithubService::class.java)

        ArchTaskExecutor.getInstance().setDelegate(object : TaskExecutor() {
            override fun executeOnDiskIO(runnable: Runnable) = runnable.run()
            override fun isMainThread() = true
            override fun postToMainThread(runnable: Runnable) = runnable.run()
        })
    }

    @Throws(IOException::class)
    @After
    fun stopService() {
        mockWebServer.shutdown()
        ArchTaskExecutor.getInstance().setDelegate(null)
    }

    @Test
    fun getGithubUserTest() {
        enqueueResponse("user-octocat.json")
        val githubUser = LiveDataTestUtil.getValue(service.fetchGithubUser("octocat")).body

        val request: RecordedRequest = mockWebServer.takeRequest()
        assertThat(request.path, `is`("/users/octocat"))

        assertThat(githubUser, notNullValue())
        assertThat(githubUser?.login, `is`("octocat"))
        assertThat(githubUser?.id, `is`(583231))
        assertThat(githubUser?.avatar_url, `is`("https://avatars3.githubusercontent.com/u/583231?v=4"))
        assertThat(githubUser?.url, `is`("https://api.github.com/users/octocat"))
    }

    @Test
    fun getReposTest() {
        enqueueResponse("repos-octocat.json")
        val repos = LiveDataTestUtil.getValue(service.fetchRepos("octocat", 0)).body

        val request: RecordedRequest = mockWebServer.takeRequest()
        assertThat(request.path, `is`("/users/octocat/repos?page=0"))

        assertThat(repos, notNullValue())
        assertThat(repos?.size, `is`(8))
        assertThat(repos?.get(0)?.name, `is`("boysenberry-repo-1"))
    }

    @Test
    fun getCommitsTest() {
        enqueueResponse("commits-git-consortium.json")
        val commits = LiveDataTestUtil.getValue(service.fetchCommits("octocat", "git-consortium", 0)).body

        val request: RecordedRequest = mockWebServer.takeRequest()
        assertThat(request.path, `is`("/repos/octocat/git-consortium/commits?page=0"))

        assertThat(commits, notNullValue())
        assertThat(commits?.size, `is`(6))
        assertThat(commits?.get(0)?.commit?.author?.name, `is`("The Octocat"))
    }

    @Throws(IOException::class)
    private fun enqueueResponse(fileName: String) {
        enqueueResponse(fileName, emptyMap())
    }

    @Throws(IOException::class)
    private fun enqueueResponse(fileName: String, headers: Map<String, String>) {
        val inputStream = javaClass.classLoader?.getResourceAsStream("api-response/$fileName")
        val source = inputStream?.let { it.source() }!!.buffer()
        val mockResponse = MockResponse()
        for ((key, value) in headers) {
            mockResponse.addHeader(key, value)
        }
        mockWebServer.enqueue(mockResponse.setBody(source.readString(StandardCharsets.UTF_8)))
    }
}
