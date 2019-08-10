package org.michaelbel.moviemade.core

import android.Manifest.permission.VIBRATE
import android.content.Context
import android.content.Context.VIBRATOR_SERVICE
import android.content.Context.WINDOW_SERVICE
import android.content.res.Configuration.ORIENTATION_LANDSCAPE
import android.os.Build
import android.os.VibrationEffect
import android.os.VibrationEffect.DEFAULT_AMPLITUDE
import android.os.Vibrator
import android.util.DisplayMetrics
import android.view.WindowManager
import androidx.annotation.RequiresPermission
import org.michaelbel.moviemade.R
import kotlin.math.ceil

object DeviceUtil {

    fun isTablet(context: Context) = context.resources.getBoolean(R.bool.tablet)

    fun isLandscape(context: Context) =
            context.resources.configuration.orientation == ORIENTATION_LANDSCAPE

    fun statusBarHeight(context: Context): Int {
        val resId = context.resources.getIdentifier("status_bar_height", "dimen", "android")
        return if (resId > 0) context.resources.getDimensionPixelSize(resId) else 0
    }

    fun dp(context: Context, value: Float) =
            ceil((context.resources.displayMetrics.density * value).toDouble()).toInt()

    @Suppress("deprecation")
    @RequiresPermission(VIBRATE)
    fun vibrate(context: Context, milliseconds: Int) {
        val vibrator = context.getSystemService(VIBRATOR_SERVICE) as Vibrator
        if (Build.VERSION.SDK_INT > 26) {
            vibrator.vibrate(VibrationEffect.createOneShot(milliseconds.toLong(), DEFAULT_AMPLITUDE))
        } else {
            // Deprecated in API 26
            vibrator.vibrate(milliseconds.toLong())
        }
    }

    fun getScreenWidth(context: Context): Int {
        val windowManager = context.getSystemService(WINDOW_SERVICE) as WindowManager
        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        return displayMetrics.widthPixels
    }
}