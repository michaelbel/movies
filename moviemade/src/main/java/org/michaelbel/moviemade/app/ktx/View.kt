package org.michaelbel.moviemade.app.ktx

import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import androidx.core.view.marginTop
import androidx.core.view.updateLayoutParams

var View.topPadding: Int
    inline get() = paddingTop
    set(value) = setPadding(paddingLeft, value, paddingRight, paddingBottom)

var View.topMargin: Int
    inline get() = marginTop
    set(value) = updateLayoutParams<ViewGroup.MarginLayoutParams> { topMargin = value }

fun View.setOnGlobalLayoutListenerSingle(onGlobalLayoutAction: () -> Unit) {
    viewTreeObserver.addOnGlobalLayoutListener(object: ViewTreeObserver.OnGlobalLayoutListener {
        override fun onGlobalLayout() {
            viewTreeObserver.removeOnGlobalLayoutListener(this)
            onGlobalLayoutAction()
        }
    })
}