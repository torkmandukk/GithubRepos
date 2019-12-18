package com.torkmandukk.githubrepos.extension

import androidx.recyclerview.widget.RecyclerView
import com.torkmandukk.githubrepos.models.Resource
import com.torkmandukk.githubrepos.models.Status
import org.jetbrains.anko.toast

fun RecyclerView.bindResource(resource: Resource<Any>, onSuccess: () -> Unit) {
    when (resource.status) {
        Status.LOADING -> Unit
        Status.SUCCESS -> onSuccess()
        Status.ERROR -> this.context.toast(resource.message.toString())
    }
}
