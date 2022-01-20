package org.michaelbel.moviemade.app.ktx

import android.content.Context

inline val Context.statusBarHeight: Int
    get() {
        val resId = resources.getIdentifier("status_bar_height", "dimen", "android")
        return if (resId > 0) resources.getDimensionPixelSize(resId) else 0
    }