package com.torkmandukk.githubrepos.view.viewholder

import android.view.View
import androidx.databinding.DataBindingUtil
import com.skydoves.baserecyclerviewadapter.BaseViewHolder
import com.torkmandukk.githubrepos.databinding.ItemGithubCommitBinding
import com.torkmandukk.githubrepos.models.GitCommit

class GithubCommitViewHolder(view: View) : BaseViewHolder(view) {

    private lateinit var gitCommit: GitCommit
    private val binding by lazy { DataBindingUtil.bind<ItemGithubCommitBinding>(view) }

    @Throws(Exception::class)
    override fun bindData(data: Any) {
        if (data is GitCommit) {
            gitCommit = data
            drawUI()
        }
    }

    private fun drawUI() {
        itemView.run {
            binding?.gitCommit = gitCommit
            binding?.executePendingBindings()
        }
    }

    override fun onClick(view: View) = Unit

    override fun onLongClick(view: View) = false
}
