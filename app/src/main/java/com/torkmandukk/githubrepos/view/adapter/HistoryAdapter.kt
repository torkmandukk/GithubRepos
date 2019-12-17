package com.torkmandukk.githubrepos.view.adapter

import android.view.View
import com.skydoves.baserecyclerviewadapter.BaseAdapter
import com.skydoves.baserecyclerviewadapter.SectionRow
import com.torkmandukk.githubrepos.R
import com.torkmandukk.githubrepos.models.History
import com.torkmandukk.githubrepos.view.viewholder.HistoryViewHolder

@Suppress("PrivatePropertyName")
class HistoryAdapter(
        private val delegate: HistoryViewHolder.Delegate
) : BaseAdapter() {

    private val section_history = 0

    init {
        addSection(ArrayList<History>())
    }

    fun updateItemList(histories: List<History>) {
        sections()[section_history].clear()
        sections()[section_history].addAll(histories)
        notifyDataSetChanged()
    }

    override fun layout(sectionRow: SectionRow) = R.layout.item_history

    override fun viewHolder(layout: Int, view: View) = HistoryViewHolder(view, delegate)
}
