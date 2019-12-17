package com.torkmandukk.githubrepos.view.viewholder

import android.view.View
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.skydoves.baserecyclerviewadapter.BaseViewHolder
import com.torkmandukk.githubrepos.databinding.LayoutDetailHeaderBinding
import com.torkmandukk.githubrepos.models.GithubUser
import kotlinx.android.synthetic.main.layout_detail_header.view.*

class GithubUserHeaderViewHolder(
  private val view: View,
  private val delegate: Delegate
) : BaseViewHolder(view) {

  private lateinit var githubUser: GithubUser
  val binding by lazy { DataBindingUtil.bind<LayoutDetailHeaderBinding>(view) }

  interface Delegate {
    fun onCardClicked(githubUser: GithubUser)
  }

  @Throws(Exception::class)
  override fun bindData(data: Any) {
    if (data is GithubUser) {
      githubUser = data
      drawUserCard()
    }
  }

  private fun drawUserCard() {
    itemView.run {
      binding?.githubUser = githubUser
      binding?.executePendingBindings()

      Glide.with(context)
        .load(githubUser.avatar_url)
        .apply(RequestOptions().circleCrop())
        .into(detail_header_avatar)
    }
  }

  override fun onClick(view: View) {
    delegate.onCardClicked(githubUser)
  }

  override fun onLongClick(view: View) = false
}
