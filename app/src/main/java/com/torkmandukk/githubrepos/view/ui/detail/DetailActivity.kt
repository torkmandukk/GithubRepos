package com.torkmandukk.githubrepos.view.ui.detail

import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.torkmandukk.githubrepos.R
import com.torkmandukk.githubrepos.databinding.ActivityDetailBinding
import com.torkmandukk.githubrepos.extension.fromResource
import com.torkmandukk.githubrepos.extension.observeLiveData
import com.torkmandukk.githubrepos.factory.AppViewModelFactory
import com.torkmandukk.githubrepos.models.GithubUser
import com.torkmandukk.githubrepos.models.ItemDetail
import com.torkmandukk.githubrepos.models.Resource
import com.torkmandukk.githubrepos.models.Status
import com.torkmandukk.githubrepos.view.adapter.DetailAdapter
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.layout_detail_body.*
import kotlinx.android.synthetic.main.layout_detail_header.*
import kotlinx.android.synthetic.main.toolbar_default.view.*
import org.jetbrains.anko.toast
import javax.inject.Inject
//import javax.inject.InjectViewModelProvider

class DetailActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: AppViewModelFactory

    private val viewModel by lazy { ViewModelProviders.of(this, viewModelFactory).get(DetailActivityViewModel::class.java) }
    private val binding by lazy { DataBindingUtil.setContentView<ActivityDetailBinding>(this, R.layout.activity_detail) }
    private val adapter by lazy { DetailAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        supportPostponeEnterTransition()

        initializeUI()
    }

    private fun initializeUI() {
        binding.detailToolbar.toolbar_title?.text = getLoginFromIntent()
        Glide.with(this)
                .load(getAvatarFromIntent())
                .apply(RequestOptions().circleCrop().dontAnimate())
                .listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
                        supportStartPostponedEnterTransition()
                        observeViewModel()
                        return false
                    }

                    override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                        supportStartPostponedEnterTransition()
                        observeViewModel()
                        return false
                    }
                })
                .into(detail_header_avatar)

        detail_body_recyclerViewFrame.setLayoutManager(LinearLayoutManager(this))
        detail_body_recyclerViewFrame.setAdapter(adapter)
    }

    private fun observeViewModel() {
        viewModel.setUser(getLoginFromIntent())
        observeLiveData(viewModel.githubUserLiveData) { updateUI(it) }
    }

    private fun updateUI(resource: Resource<GithubUser>) {
        when (resource.status) {
            Status.SUCCESS -> {
                resource.data?.let {
                    binding.detailHeader.githubUser = it
                    binding.executePendingBindings()

                    val itemList = ArrayList<ItemDetail>()
                    itemList.add(ItemDetail(fromResource(this, R.drawable.ic_person_pin), it.html_url))
                    it.company?.let { itemList.add(ItemDetail(fromResource(this, R.drawable.ic_people), it)) }
                    it.location?.let { itemList.add(ItemDetail(fromResource(this, R.drawable.ic_location), it)) }
                    it.blog?.let { itemList.add(ItemDetail(fromResource(this, R.drawable.ic_insert_link), it)) }
                    adapter.addItemDetailList(itemList)
                    detail_body_recyclerViewFrame.addVeiledItems(itemList.size)
                }
            }
            Status.ERROR -> toast(resource.message.toString())
            Status.LOADING -> Unit
        }
    }

    private fun getLoginFromIntent(): String {
        return intent.getStringExtra(intent_login)
    }

    private fun getAvatarFromIntent(): String {
        return intent.getStringExtra(intent_avatar)
    }

    companion object {
        const val intent_login = "login"
        const val intent_avatar = "avatar_url"
        const val intent_requestCode = 1000
    }
}
