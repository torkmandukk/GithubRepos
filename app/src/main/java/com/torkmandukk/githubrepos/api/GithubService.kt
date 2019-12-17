package com.torkmandukk.githubrepos.api

import androidx.lifecycle.LiveData
import com.torkmandukk.githubrepos.models.GitCommit
import com.torkmandukk.githubrepos.models.GithubUser
import com.torkmandukk.githubrepos.models.Repo
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GithubService {
    /**
     * [Users](https://developer.github.com/v3/users/)
     *
     * Provides publicly available information about someone with a GitHub account.
     *
     * @param [user] the name of the user.
     *
     * @return [GithubUser] github user model.
     */
    @GET("/users/{user}")
    fun fetchGithubUser(@Path("user") user: String): LiveData<ApiResponse<GithubUser>>

    /**
     * [Repos](https://developer.github.com/v3/repos)
     *
     * List users repositories.
     *
     * @param [user] the name of the user.
     * @param [page] the number of a page.
     *
     * @return [Repo] github repository model.
     */
    @GET("/users/{user}/repos")
    fun fetchRepos(@Path("user") user: String, @Query("page") page: Int): LiveData<ApiResponse<List<Repo>>>

    /**
     * [Commits](https://developer.github.com/v3/repos/commits)
     *
     * List users repositories.
     *
     * @param [user] the name of the user.
     * @param [repo] the name of repository.
     *
     * @return [GitCommit] github commit model.
     */
    @GET("/repos/{user}/{repo}/commits")
    fun fetchCommits(@Path("user") user: String, @Path("repo") repo: String, @Query("page") page: Int): LiveData<ApiResponse<List<GitCommit>>>
}
