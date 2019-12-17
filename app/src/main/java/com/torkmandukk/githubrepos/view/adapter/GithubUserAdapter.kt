package com.torkmandukk.githubrepos.view.adapter

import android.view.View
import com.skydoves.baserecyclerviewadapter.BaseAdapter
import com.skydoves.baserecyclerviewadapter.BaseViewHolder
import com.skydoves.baserecyclerviewadapter.SectionRow
import com.torkmandukk.githubrepos.R
import com.torkmandukk.githubrepos.models.GithubUser
import com.torkmandukk.githubrepos.models.Repo
import com.torkmandukk.githubrepos.models.Resource
import com.torkmandukk.githubrepos.view.viewholder.GithubUserHeaderViewHolder
import com.torkmandukk.githubrepos.view.viewholder.GithubRepoViewHolder

@Suppress("PrivatePropertyName", "MemberVisibilityCanBePrivate")
class GithubUserAdapter(
        private val delegate_header: GithubUserHeaderViewHolder.Delegate,
        private val delegate: GithubRepoViewHolder.Delegate
) : BaseAdapter() {

    private val section_header = 0
    private val section_info = 1
    private val section_repo = 2

    init {
        addSection(ArrayList<GithubUser>())
        addSection(ArrayList<Any>())
        addSection(ArrayList<Repo>())
    }

    fun updateHeader(resource: Resource<GithubUser>) {
        resource.data?.let {
            sections()[section_header].clear()
            sections()[section_header].add(it)
            notifyDataSetChanged()
        }
    }

    fun addReposList(repos: List<Repo>) {
        if (repos.isEmpty()) return
        sections()[section_info].add(Any())
        sections()[section_repo].addAll(repos)
        notifyDataSetChanged()
    }

    fun clearAll() {
        sections()[section_header].clear()
        sections()[section_info].clear()
        sections()[section_repo].clear()
        notifyDataSetChanged()
    }

    override fun layout(sectionRow: SectionRow): Int {
        if (sectionRow.section == section_header) return R.layout.layout_detail_header
        if (sectionRow.section == section_info) return R.layout.item_info
        return R.layout.item_github_repo
    }

    override fun viewHolder(layout: Int, view: View): BaseViewHolder {
        return when (layout) {
            R.layout.layout_detail_header -> GithubUserHeaderViewHolder(view, delegate_header)
            else -> GithubRepoViewHolder(view, delegate)
        }
    }
}
