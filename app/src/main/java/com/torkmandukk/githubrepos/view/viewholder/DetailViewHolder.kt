package com.torkmandukk.githubrepos.view.viewholder

import android.text.util.Linkify
import android.util.Patterns
import android.view.View
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.skydoves.baserecyclerviewadapter.BaseViewHolder
import com.torkmandukk.githubrepos.R
import com.torkmandukk.githubrepos.databinding.ItemDetailInfoBinding
import com.torkmandukk.githubrepos.models.ItemDetail
import kotlinx.android.synthetic.main.item_detail_info.view.*
import org.jetbrains.anko.textColor

class DetailViewHolder(view: View) :
        BaseViewHolder(view) {

    private lateinit var itemDetail: ItemDetail
    private val binding by lazy { DataBindingUtil.bind<ItemDetailInfoBinding>(view) }

    @Throws(Exception::class)
    override fun bindData(data: Any) {
        if (data is ItemDetail) {
            itemDetail = data
            drawItem()
        }
    }

    private fun drawItem() {
        itemView.run {
            binding?.itemDetail = itemDetail
            binding?.executePendingBindings()
            if (Patterns.WEB_URL.matcher(itemDetail.content).matches()) {
                detail_info_content.textColor = ContextCompat.getColor(context, R.color.colorPrimaryDark)
                Linkify.addLinks(detail_info_content, Linkify.WEB_URLS)
            } else {
                detail_info_content.textColor = ContextCompat.getColor(context, R.color.white)
            }
        }
    }

    override fun onClick(view: View) = Unit

    override fun onLongClick(view: View) = false
}
