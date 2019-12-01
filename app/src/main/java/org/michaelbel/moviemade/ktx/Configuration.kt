@file:Suppress("nothing_to_inline", "unused")

package org.michaelbel.moviemade.ktx

import android.Manifest.permission.ACCESS_NETWORK_STATE
import android.Manifest.permission.VIBRATE
import android.content.Context
import android.content.Context.CONNECTIVITY_SERVICE
import android.content.Context.VIBRATOR_SERVICE
import android.content.Intent
import android.content.res.Configuration.*
import android.graphics.BlendMode
import android.graphics.BlendModeColorFilter
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.net.ConnectivityManager
import android.net.Uri
import android.os.Build
import android.os.VibrationEffect
import android.os.VibrationEffect.DEFAULT_AMPLITUDE
import android.os.Vibrator
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.RequiresPermission
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat.LAYOUT_DIRECTION_LTR
import androidx.core.view.ViewCompat.LAYOUT_DIRECTION_RTL

inline val Context.isTablet: Boolean
    get() = resources.getBoolean(org.michaelbel.moviemade.R.bool.tablet)

inline val Context.displayWidth: Int
    get() = resources.displayMetrics.widthPixels

inline val Context.displayHeight: Int
    get() = resources.displayMetrics.heightPixels

inline val Context.isPortrait: Boolean
    get() = resources.configuration.orientation == ORIENTATION_PORTRAIT

inline val Context.isLandscape: Boolean
    get() = resources.configuration.orientation == ORIENTATION_LANDSCAPE

inline val Context.isUndefined: Boolean
    get() = resources.configuration.orientation == ORIENTATION_UNDEFINED

inline val Context.isRTL: Boolean
    get() = resources.configuration.layoutDirection == LAYOUT_DIRECTION_RTL

inline val Context.isLTR: Boolean
    get() = resources.configuration.layoutDirection == LAYOUT_DIRECTION_LTR

inline val Context.statusBarHeight: Int
    get() {
        val resId = resources.getIdentifier("status_bar_height", "dimen", "android")
        return if (resId > 0) resources.getDimensionPixelSize(resId) else 0
    }

/**
 * Execute [f] only if the current Android SDK version is [version] or older.
 * Do nothing otherwise.
 */
inline fun doBeforeSdk(version: Int, f: () -> Unit) {
    if (Build.VERSION.SDK_INT <= version) f()
}

/**
 * Execute [f] only if the current Android SDK version is [version] or newer.
 * Do nothing otherwise.
 */
inline fun doFromSdk(version: Int, f: () -> Unit) {
    if (Build.VERSION.SDK_INT >= version) f()
}

/**
 * Execute [f] only if the current Android SDK version is [version].
 * Do nothing otherwise.
 */
inline fun doIfSdk(version: Int, f: () -> Unit) {
    if (Build.VERSION.SDK_INT == version) f()
}

// todo
@RequiresPermission(ACCESS_NETWORK_STATE)
inline fun Context.isNetworkAvailable(): Boolean {
    val connectivityManager = this.getSystemService(CONNECTIVITY_SERVICE) as? ConnectivityManager
    connectivityManager?.let {
        val netInfo = it.activeNetworkInfo
        netInfo?.let { info ->
            if (info.isConnected) return true
        }
    }
    return false
}

// todo
inline fun Context.getIcon(@DrawableRes resource: Int, @ColorRes color: Int): Drawable? {
    val iconDrawable = ContextCompat.getDrawable(this, resource) ?: getDrawable(resource)
    val colorDrawable = ContextCompat.getColor(this, color)

    iconDrawable?.clearColorFilter()
    iconDrawable?.mutate()
    if (Build.VERSION.SDK_INT >= 29) {
        iconDrawable?.colorFilter = BlendModeColorFilter(colorDrawable, BlendMode.COLOR)
    } else {
        iconDrawable?.setColorFilter(colorDrawable, PorterDuff.Mode.MULTIPLY)
    }

    return iconDrawable
}

// todo
@RequiresPermission(VIBRATE)
inline fun Context.vibrate(milliseconds: Int) {
    val vibrator = getSystemService(VIBRATOR_SERVICE) as Vibrator
    if (Build.VERSION.SDK_INT > 26) {
        vibrator.vibrate(VibrationEffect.createOneShot(milliseconds.toLong(), DEFAULT_AMPLITUDE))
    } else {
        // Deprecated in API 26
        @Suppress("deprecation")
        vibrator.vibrate(milliseconds.toLong())
    }
}

// todo IsScreenLock

fun Context.sendEmail(email: String?) {
    if (email != null) {
        startActivity(Intent.createChooser(Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", email, null)), null))
    }
}