package com.torkmandukk.githubrepos.view.viewholder

import android.view.View
import androidx.databinding.DataBindingUtil
import com.skydoves.baserecyclerviewadapter.BaseViewHolder
import com.torkmandukk.githubrepos.databinding.ItemGithubRepoBinding
import com.torkmandukk.githubrepos.models.Repo

class GithubRepoViewHolder(
        private val view: View,
        private val delegate: Delegate
) : BaseViewHolder(view) {

    interface Delegate {
        fun onItemClick(githubRepo: Repo, view: View)
    }

    private lateinit var githubRepo: Repo
    private val binding by lazy { DataBindingUtil.bind<ItemGithubRepoBinding>(view) }

    @Throws(Exception::class)
    override fun bindData(data: Any) {
        if (data is Repo) {
            githubRepo = data
            drawUI()
        }
    }

    private fun drawUI() {
        itemView.run {
            binding?.repo = githubRepo
            binding?.executePendingBindings()
        }
    }

    override fun onClick(view: View) {
        if (this::githubRepo.isInitialized) delegate.onItemClick(githubRepo, itemView)
    }

    override fun onLongClick(view: View) = false
}
