@file:Suppress("unused")

package com.torkmandukk.githubrepos.extension

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.app.Activity
import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Build
import android.view.View
import android.view.ViewAnimationUtils
import android.view.animation.AccelerateInterpolator
import androidx.core.content.ContextCompat
import com.torkmandukk.githubrepos.R

fun Activity.checkIsMaterialVersion() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP

fun Activity.circularRevealed(view: View, x: Int, y: Int) {
    if (checkIsMaterialVersion()) {
        val finalRadius = (Math.max(view.width, view.height) * 1.1)
        val circularReveal = ViewAnimationUtils.createCircularReveal(view, x, y, 0f, finalRadius.toFloat())
        circularReveal.duration = 400
        circularReveal.interpolator = AccelerateInterpolator()

        view.setBackgroundColor(ContextCompat.getColor(this, R.color.background800))
        view.visible()
        circularReveal.start()
    }
}

fun Activity.circularUnRevealed(mView: View, revealX: Int, revealY: Int) {
    if (checkIsMaterialVersion()) {
        val finalRadius = (Math.max(mView.width, mView.height) * 1.1)
        val circularReveal = ViewAnimationUtils.createCircularReveal(
                mView, revealX, revealY, finalRadius.toFloat(), 0f)

        circularReveal.duration = 350
        circularReveal.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                mView.inVisible()
                finish()
                overridePendingTransition(0, 0)
            }
        })

        circularReveal.start()
    }
}

fun Activity.fromResource(context: Context, layout: Int): Drawable {
    return ContextCompat.getDrawable(context, layout)!!
}
