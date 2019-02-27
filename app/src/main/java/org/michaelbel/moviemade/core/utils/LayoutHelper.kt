package org.michaelbel.moviemade.core.utils

import android.widget.LinearLayout
import org.michaelbel.moviemade.presentation.App

object LayoutHelper {

    private fun getSize(size: Float): Int {
        return if (size < 0) {
            size.toInt()
        } else {
            DeviceUtil.dp(App.appContext, size)
        }
    }

    fun makeLinear(width: Int, height: Int): LinearLayout.LayoutParams =
            LinearLayout.LayoutParams(getSize(width.toFloat()), getSize(height.toFloat()))
}