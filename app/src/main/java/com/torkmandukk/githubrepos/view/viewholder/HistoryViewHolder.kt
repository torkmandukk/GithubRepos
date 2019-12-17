package com.torkmandukk.githubrepos.view.viewholder

import android.view.View
import androidx.databinding.DataBindingUtil
import com.skydoves.baserecyclerviewadapter.BaseViewHolder
import com.torkmandukk.githubrepos.databinding.ItemHistoryBinding
import com.torkmandukk.githubrepos.models.History
import kotlinx.android.synthetic.main.item_history.view.*

class HistoryViewHolder(
        private val view: View,
        private val delegate: Delegate
) : BaseViewHolder(view) {

    interface Delegate {
        fun onItemClicked(history: History)
        fun onDeleteHistory(history: History)
    }

    private lateinit var history: History
    private val binding by lazy { DataBindingUtil.bind<ItemHistoryBinding>(view) }

    @Throws(Exception::class)
    override fun bindData(data: Any) {
        if (data is History) {
            history = data
            drawItemView()
        }
    }

    private fun drawItemView() {
        itemView.run {
            binding?.history = history
            binding?.executePendingBindings()
            item_history_delete.setOnClickListener { delegate.onDeleteHistory(history) }
        }
    }

    override fun onClick(view: View) {
        delegate.onItemClicked(history)
    }

    override fun onLongClick(view: View) = false
}
