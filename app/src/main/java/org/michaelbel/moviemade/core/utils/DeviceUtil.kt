package org.michaelbel.moviemade.core.utils

import android.Manifest
import android.content.Context
import android.content.res.Configuration
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import androidx.annotation.RequiresPermission
import org.michaelbel.moviemade.R

object DeviceUtil {

    fun isTablet(context: Context): Boolean = context.resources.getBoolean(R.bool.tablet)

    fun isLandscape(context: Context): Boolean =
            context.resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

    fun getStatusBarHeight(context: Context): Int {
        var result = 0
        val resId = context.resources.getIdentifier("status_bar_height", "dimen", "android")

        if (resId > 0) {
            result = context.resources.getDimensionPixelSize(resId)
        }

        return result
    }

    fun dp(context: Context, value: Float): Int =
            Math.ceil((context.resources.displayMetrics.density * value).toDouble()).toInt()

    @RequiresPermission(Manifest.permission.VIBRATE)
    fun vibrate(context: Context, milliseconds: Int) {
        val vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        if (Build.VERSION.SDK_INT > 26) {
            vibrator.vibrate(VibrationEffect.createOneShot(
                    milliseconds.toLong(),
                    VibrationEffect.DEFAULT_AMPLITUDE)
            )
        } else {
            // Deprecated in API 26
            vibrator.vibrate(milliseconds.toLong())
        }
    }
}