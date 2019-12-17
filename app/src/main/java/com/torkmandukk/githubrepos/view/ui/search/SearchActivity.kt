package com.torkmandukk.githubrepos.view.ui.search

import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.view.ViewTreeObserver
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.torkmandukk.githubrepos.R
import com.torkmandukk.githubrepos.databinding.ActivitySearchBinding
import com.torkmandukk.githubrepos.extension.checkIsMaterialVersion
import com.torkmandukk.githubrepos.extension.circularRevealed
import com.torkmandukk.githubrepos.extension.circularUnRevealed
import com.torkmandukk.githubrepos.extension.inVisible
import com.torkmandukk.githubrepos.extension.observeLiveData
import com.torkmandukk.githubrepos.extension.vm
import com.torkmandukk.githubrepos.factory.AppViewModelFactory
import com.torkmandukk.githubrepos.models.GithubUser
import com.torkmandukk.githubrepos.models.History
import com.torkmandukk.githubrepos.models.Resource
import com.torkmandukk.githubrepos.models.Status
import com.torkmandukk.githubrepos.view.adapter.HistoryAdapter
import com.torkmandukk.githubrepos.view.viewholder.HistoryViewHolder
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_search.*
import kotlinx.android.synthetic.main.toolbar_search.*
import org.jetbrains.anko.toast
import javax.inject.Inject

class SearchActivity : AppCompatActivity(),
  TextView.OnEditorActionListener,
  HistoryViewHolder.Delegate {

  @Inject
  lateinit var viewModelFactory: AppViewModelFactory
  private val viewModel by lazy { vm(viewModelFactory, SearchActivityViewModel::class) }
  private lateinit var binding: ActivitySearchBinding

  override fun onCreate(savedInstanceState: Bundle?) {
    AndroidInjection.inject(this)
    super.onCreate(savedInstanceState)
    binding = DataBindingUtil.setContentView(this, R.layout.activity_search)
    binding.viewModel = viewModel
    binding.lifecycleOwner = this

    initializeUI()
    startCircularRevealed(savedInstanceState)
    observeLiveData(viewModel.githubUserLiveData) { onChangeUser(it) }
  }

  private fun startCircularRevealed(savedInstanceState: Bundle?) {
    if (savedInstanceState == null && checkIsMaterialVersion()) {
      search_layout.inVisible()

      val viewTreeObserver = search_layout.viewTreeObserver
      if (viewTreeObserver.isAlive) {
        viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
          override fun onGlobalLayout() {
            circularRevealed(search_layout, search_layout.width, 0)
            search_layout.viewTreeObserver.removeOnGlobalLayoutListener(this)
          }
        })
      }
    }
  }

  private fun initializeUI() {
    search_recyclerView.layoutManager = LinearLayoutManager(this)
    search_recyclerView.adapter = HistoryAdapter(this)
    toolbar_search_home.setOnClickListener { onBackPressed() }
    toolbar_search_input.setOnEditorActionListener(this)
  }

  override fun onEditorAction(p0: TextView?, actionId: Int, event: KeyEvent?): Boolean {
    val searchKeyword = toolbar_search_input.text
    if (actionId == EditorInfo.IME_ACTION_SEARCH) {
      searchKeyword?.let {
        viewModel.login.postValue(it.toString())
        return true
      }
    }
    return false
  }

  override fun onItemClicked(history: History) {
    onSetResult(history.search)
  }

  override fun onDeleteHistory(history: History) {
    viewModel.deleteHistory(history)
  }

  private fun onChangeUser(resource: Resource<GithubUser>) {
    when (resource.status) {
      Status.SUCCESS -> onSetResult(resource.data?.login!!)
      Status.ERROR -> toast(resource.message.toString())
      Status.LOADING -> Unit
    }
  }

  private fun onSetResult(user: String) {
    viewModel.insertHistory(user)
    setResult(intent_requestCode, Intent().putExtra(viewModel.getPreferenceUserKeyName(), user))
    onBackPressed()
  }

  override fun onBackPressed() {
    when (checkIsMaterialVersion()) {
      true -> circularUnRevealed(search_layout, search_layout.width, 0)
      false -> super.onBackPressed()
    }
  }

  companion object {
    const val intent_requestCode = 1001
  }
}
