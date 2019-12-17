package com.torkmandukk.githubrepos.utils

import android.content.Context
import android.graphics.drawable.PictureDrawable
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.torkmandukk.githubrepos.R

class GlideUtils {
    companion object {
        fun getSvgRequestBuilder(context: Context): RequestBuilder<PictureDrawable> {
            return GlideApp.with(context)
                    .`as`(PictureDrawable::class.java)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .error(R.drawable.rectangle)
                    .listener(SvgSoftwareLayerSetter())
        }
    }
}
