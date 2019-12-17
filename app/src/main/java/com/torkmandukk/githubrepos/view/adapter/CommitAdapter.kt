package com.torkmandukk.githubrepos.view.adapter

import android.view.View
import com.skydoves.baserecyclerviewadapter.BaseAdapter
import com.skydoves.baserecyclerviewadapter.BaseViewHolder
import com.skydoves.baserecyclerviewadapter.SectionRow
import com.torkmandukk.githubrepos.R
import com.torkmandukk.githubrepos.models.GitCommit
import com.torkmandukk.githubrepos.view.viewholder.GithubCommitViewHolder

@Suppress("PrivatePropertyName", "MemberVisibilityCanBePrivate")
class CommitAdapter : BaseAdapter() {

    private val section_commit = 0

    init {
//        addSection(ArrayList<GithubUser>())
//        addSection(ArrayList<Any>())
        addSection(ArrayList<GitCommit>())
    }

    fun addCommitsList(gitCommits: List<GitCommit>) {
        if (gitCommits.isEmpty()) return
        sections()[section_commit].addAll(gitCommits)
        notifyDataSetChanged()
    }

    fun clearAll() {
        sections()[section_commit].clear()
        notifyDataSetChanged()
    }

    override fun layout(sectionRow: SectionRow): Int {
        return R.layout.item_github_commit
    }

    override fun viewHolder(layout: Int, view: View): BaseViewHolder {
        return GithubCommitViewHolder(view)
    }
}
