package org.michaelbel.moviemade.utils

import android.widget.FrameLayout
import android.widget.LinearLayout

import org.michaelbel.moviemade.Moviemade

object LayoutHelper {

    const val WRAP_CONTENT = -2

    private fun getSize(size: Float): Int {
        return if (size < 0) {
            size.toInt()
        } else {
            DeviceUtil.dp(Moviemade.appContext, size)
        }
    }

    fun makeFrame(width: Int, height: Int, gravity: Int, start: Float, top: Float, end: Float, bottom: Float): FrameLayout.LayoutParams {
        val params = FrameLayout.LayoutParams(getSize(width.toFloat()), getSize(height.toFloat()))
        params.gravity = gravity
        params.leftMargin = getSize(start)
        params.topMargin = getSize(top)
        params.rightMargin = getSize(end)
        params.bottomMargin = getSize(bottom)
        return params
    }

    fun makeLinear(width: Int, height: Int): LinearLayout.LayoutParams {
        return LinearLayout.LayoutParams(getSize(width.toFloat()), getSize(height.toFloat()))
    }
}