package com.torkmandukk.githubrepos.binding

import android.view.View
import androidx.databinding.BindingConversion

@BindingConversion
fun bindingVisibility(visible: Boolean): Int {
    return if (visible) View.VISIBLE else View.GONE
}
