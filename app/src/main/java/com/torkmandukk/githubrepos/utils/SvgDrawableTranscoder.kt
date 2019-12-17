package com.torkmandukk.githubrepos.utils

import android.graphics.drawable.PictureDrawable
import com.bumptech.glide.load.Options
import com.bumptech.glide.load.engine.Resource
import com.bumptech.glide.load.resource.SimpleResource
import com.bumptech.glide.load.resource.transcode.ResourceTranscoder
import com.caverock.androidsvg.SVG

class SvgDrawableTranscoder : ResourceTranscoder<SVG, PictureDrawable> {
    override fun transcode(
            toTranscode: Resource<SVG>,
            options: Options
    ): Resource<PictureDrawable>? {
        val svg = toTranscode.get()
        svg.documentWidth = 1000f
        svg.documentHeight = 300f
        svg.setDocumentViewBox(0f, 0f, 720f, 150f)
        val picture = svg.renderToPicture()
        val drawable = PictureDrawable(picture)
        return SimpleResource(drawable)
    }
}
