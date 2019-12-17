package com.torkmandukk.githubrepos.view.adapter

import android.view.View
import com.skydoves.baserecyclerviewadapter.BaseAdapter
import com.skydoves.baserecyclerviewadapter.SectionRow
import com.torkmandukk.githubrepos.R
import com.torkmandukk.githubrepos.models.ItemDetail
import com.torkmandukk.githubrepos.view.viewholder.DetailViewHolder

@Suppress("PrivatePropertyName")
class DetailAdapter : BaseAdapter() {

    private val section_itemDetail = 0

    fun addItemDetailList(itemDetail: List<ItemDetail>) {
        clearAllSections()
        addSection(ArrayList<ItemDetail>())
        for (item in itemDetail) {
            if (item.content.isNotEmpty()) {
                addItemOnSection(section_itemDetail, item)
            }
        }
        notifyDataSetChanged()
    }

    override fun layout(sectionRow: SectionRow) = R.layout.item_detail_info

    override fun viewHolder(layout: Int, view: View) = DetailViewHolder(view)
}
