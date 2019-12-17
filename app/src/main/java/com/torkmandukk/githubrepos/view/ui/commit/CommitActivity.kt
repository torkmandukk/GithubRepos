package com.torkmandukk.githubrepos.view.ui.commit

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.skydoves.baserecyclerviewadapter.RecyclerViewPaginator
import com.torkmandukk.githubrepos.R
import com.torkmandukk.githubrepos.databinding.ActivityCommitBinding
import com.torkmandukk.githubrepos.extension.checkIsMaterialVersion
import com.torkmandukk.githubrepos.extension.observeLiveData
import com.torkmandukk.githubrepos.extension.vm
import com.torkmandukk.githubrepos.factory.AppViewModelFactory
import com.torkmandukk.githubrepos.models.GitCommit
import com.torkmandukk.githubrepos.models.Repo
import com.torkmandukk.githubrepos.models.Resource
import com.torkmandukk.githubrepos.models.Status
import com.torkmandukk.githubrepos.view.adapter.CommitAdapter
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_commit.*
import org.jetbrains.anko.startActivityForResult
import org.jetbrains.anko.toast
import javax.inject.Inject

@Suppress("SpellCheckingInspection")
class CommitActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: AppViewModelFactory
    private val viewModel by lazy { vm(viewModelFactory, CommitActivityViewModel::class) }
    private lateinit var binding: ActivityCommitBinding
    private lateinit var paginator: RecyclerViewPaginator
    private val adapter by lazy { CommitAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        supportPostponeEnterTransition()
        observeViewModel()
        binding = DataBindingUtil.setContentView(this, R.layout.activity_commit)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        commit_recyclerView.adapter = adapter
        commit_recyclerView.layoutManager = LinearLayoutManager(this)
        paginator = RecyclerViewPaginator(
                recyclerView = commit_recyclerView,
                isLoading = { viewModel.fetchStatus.isOnLoading },
                loadMore = { loadMore(it) },
                onLast = { viewModel.fetchStatus.isOnLast }
        )
    }

    private fun getUserFromIntent(): String {
        return intent.getStringExtra(intent_user)
    }

    private fun getRepoFromIntent(): String {
        return intent.getStringExtra(intent_repo)
    }

    private fun observeViewModel() {
        viewModel.setUser(getUserFromIntent())
        viewModel.setRepo(getRepoFromIntent())
        observeLiveData(viewModel.commitsLiveData) { updateUI(it) }
    }

    private fun updateUI(resource: Resource<List<GitCommit>>) {
        when (resource.status) {
            Status.SUCCESS -> {
                resource.data?.let {
                    binding.executePendingBindings()
                }
            }
            Status.ERROR -> toast(resource.message.toString())
            Status.LOADING -> Unit
        }
    }

    private fun loadMore(page: Int) {
        viewModel.postPage(page)
    }

    companion object {
        const val intent_user = "user"
        const val intent_repo = "repo"
        const val intent_requestCode = 1000

        fun startActivity(activity: Activity, githubRepo: Repo) {
            if (activity.checkIsMaterialVersion()) {
                val intent = Intent(activity, CommitActivity::class.java)
                intent.putExtra(intent_user, githubRepo.owner.login)
                intent.putExtra(intent_repo, githubRepo.name)
                activity.startActivityForResult(intent, intent_requestCode)
            } else {
                activity.startActivityForResult<CommitActivity>(intent_requestCode, intent_user to githubRepo.owner.login, intent_repo to githubRepo.name)
            }
        }
    }
}
