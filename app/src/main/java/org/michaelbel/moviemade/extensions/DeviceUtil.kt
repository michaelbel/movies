package org.michaelbel.moviemade.extensions

import android.content.Context
import android.content.res.Configuration
import org.michaelbel.moviemade.R

object DeviceUtil {

    fun isTablet(context: Context): Boolean {
        return context.resources.getBoolean(R.bool.tablet)
    }

    fun isLandscape(context: Context): Boolean {
        return context.resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
    }

    fun getStatusBarHeight(context: Context): Int {
        var result = 0
        val resId = context.resources.getIdentifier("status_bar_height", "dimen", "android")

        if (resId > 0) {
            result = context.resources.getDimensionPixelSize(resId)
        }

        return result
    }

    fun dp(context: Context, value: Float): Int {
        return Math.ceil((context.resources.displayMetrics.density * value).toDouble()).toInt()
    }
}