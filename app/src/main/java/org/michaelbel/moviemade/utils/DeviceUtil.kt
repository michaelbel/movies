package org.michaelbel.moviemade.utils

import android.content.Context
import android.content.res.Configuration
import org.michaelbel.moviemade.R
import android.os.VibrationEffect
import android.os.Build
import android.os.Vibrator
import android.text.TextUtils

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

    fun vibrate(context: Context, milliseconds: Int) {
        val vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.O) {
            vibrator.vibrate(VibrationEffect.createOneShot(milliseconds.toLong(), VibrationEffect.DEFAULT_AMPLITUDE))
        } else {
            // Deprecated in API 26
            vibrator.vibrate(milliseconds.toLong())
        }
    }

//--Get Device name---------------------------------------------------------------------------------

    fun getDeviceName(): String? {
        val manufacturer = Build.MANUFACTURER
        val model = Build.MODEL
        return if (model.startsWith(manufacturer)) {
            capitalize(model)
        } else capitalize(manufacturer) + " " + model
    }

    private fun capitalize(str: String): String? {
        if (TextUtils.isEmpty(str)) {
            return str
        }
        val arr = str.toCharArray()
        var capitalizeNext = true

        val phrase = StringBuilder()
        for (c in arr) {
            if (capitalizeNext && Character.isLetter(c)) {
                phrase.append(Character.toUpperCase(c))
                capitalizeNext = false
                continue
            } else if (Character.isWhitespace(c)) {
                capitalizeNext = true
            }
            phrase.append(c)
        }

        return phrase.toString()
    }
}