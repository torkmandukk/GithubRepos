package com.torkmandukk.githubrepos.binding

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.torkmandukk.githubrepos.extension.bindResource
import com.torkmandukk.githubrepos.models.Resource
import com.torkmandukk.githubrepos.models.GithubUser
import com.torkmandukk.githubrepos.models.GitCommit
import com.torkmandukk.githubrepos.models.Repo
import com.torkmandukk.githubrepos.models.History
import com.torkmandukk.githubrepos.view.adapter.CommitAdapter
import com.torkmandukk.githubrepos.view.adapter.GithubUserAdapter
import com.torkmandukk.githubrepos.view.adapter.HistoryAdapter
import com.torkmandukk.githubrepos.view.ui.commit.CommitActivityViewModel
import com.torkmandukk.githubrepos.view.ui.main.MainActivityViewModel

@BindingAdapter("adapterGithubUser")
fun bindAdapterGithubUser(view: RecyclerView, resource: Resource<GithubUser>?) {
    if (resource != null) {
        view.bindResource(resource) {
            val adapter = view.adapter as? GithubUserAdapter
            adapter?.updateHeader(resource)
        }
    }
}

@BindingAdapter("adapterRepos", "viewModel")
fun bindAdapterRepos(view: RecyclerView, resource: Resource<List<Repo>>?, viewModel: MainActivityViewModel) {
    if (resource != null) {
        viewModel.fetchStatus(resource)
        view.bindResource(resource) {
            val adapter = view.adapter as? GithubUserAdapter
            adapter?.addReposList(resource.data!!)
        }
    }
}

@BindingAdapter("adapterCommits", "viewModel")
fun bindAdapterCommits(view: RecyclerView, resource: Resource<List<GitCommit>>?, viewModel: CommitActivityViewModel) {
    if (resource != null) {
        viewModel.fetchStatus(resource)
        view.bindResource(resource) {
            val adapter = view.adapter as? CommitAdapter
            adapter?.addCommitsList(resource.data!!)
        }
    }
}

@BindingAdapter("adapterHistory")
fun bindAdapterHistory(view: RecyclerView, resource: List<History>?) {
    if (resource != null) {
        val adapter = view.adapter as? HistoryAdapter
        adapter?.updateItemList(resource)
    }
}
