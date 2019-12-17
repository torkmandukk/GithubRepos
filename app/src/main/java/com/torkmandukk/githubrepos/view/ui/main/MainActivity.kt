package com.torkmandukk.githubrepos.view.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.skydoves.baserecyclerviewadapter.RecyclerViewPaginator
import com.torkmandukk.githubrepos.R
import com.torkmandukk.githubrepos.databinding.ActivityMainBinding
import com.torkmandukk.githubrepos.extension.vm
import com.torkmandukk.githubrepos.factory.AppViewModelFactory
import com.torkmandukk.githubrepos.models.GithubUser
import com.torkmandukk.githubrepos.models.Repo
import com.torkmandukk.githubrepos.view.adapter.GithubUserAdapter
import com.torkmandukk.githubrepos.view.ui.commit.CommitActivity
import com.torkmandukk.githubrepos.view.ui.detail.DetailActivity
import com.torkmandukk.githubrepos.view.ui.search.SearchActivity
import com.torkmandukk.githubrepos.view.viewholder.GithubUserHeaderViewHolder
import com.torkmandukk.githubrepos.view.viewholder.GithubRepoViewHolder
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.toolbar_main.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.startActivityForResult
import javax.inject.Inject

@Suppress("SpellCheckingInspection")
class MainActivity : AppCompatActivity(),
        GithubUserHeaderViewHolder.Delegate,
        GithubRepoViewHolder.Delegate {

    @Inject
    lateinit var viewModelFactory: AppViewModelFactory
    private val viewModel by lazy { vm(viewModelFactory, MainActivityViewModel::class) }
    private lateinit var binding: ActivityMainBinding
    private lateinit var paginator: RecyclerViewPaginator
    private val adapter by lazy { GithubUserAdapter(this, this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        main_recyclerView.adapter = adapter
        main_recyclerView.layoutManager = LinearLayoutManager(this)
        paginator = RecyclerViewPaginator(
                recyclerView = main_recyclerView,
                isLoading = { viewModel.fetchStatus.isOnLoading },
                loadMore = { loadMore(it) },
                onLast = { viewModel.fetchStatus.isOnLast }
        )

        initializeUI()
    }

    private fun initializeUI() {
        toolbar_main_search.setOnClickListener { startActivityForResult<SearchActivity>(SearchActivity.intent_requestCode) }
    }

    private fun loadMore(page: Int) {
        viewModel.postPage(page)
    }

    private fun restPagination(user: String) {
        adapter.clearAll()
        paginator.resetCurrentPage()
        viewModel.refresh(user)
    }

    override fun onCardClicked(githubUser: GithubUser) {
        startActivity<DetailActivity>(DetailActivity.intent_login to githubUser.login, DetailActivity.intent_avatar to githubUser.avatar_url)
    }

    override fun onItemClick(githubRepo: Repo, view: View) {
        CommitActivity.startActivity(this, githubRepo)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (resultCode) {
            DetailActivity.intent_requestCode, SearchActivity.intent_requestCode -> data?.let {
                restPagination(data.getStringExtra(viewModel.getUserKeyName()))
            }
        }
    }
}
