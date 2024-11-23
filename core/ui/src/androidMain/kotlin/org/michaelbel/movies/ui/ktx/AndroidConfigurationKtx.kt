package org.michaelbel.movies.ui.ktx

import android.content.Context
import android.os.Build
import android.util.DisplayMetrics
import android.view.WindowManager
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.displayCutout
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.core.content.ContextCompat

val screenWidth: Dp
    @Composable get() {
        val context = LocalContext.current
        val density = LocalDensity.current
        return density.run { context.deviceWidth.toDp() }
    }

val screenHeight: Dp
    @Composable get() {
        val context = LocalContext.current
        val density = LocalDensity.current
        return density.run { context.deviceHeight.toDp() }
    }

val displayCutoutWindowInsets: WindowInsets
    @Composable get() = if (isPortrait) WindowInsets(0, 0, 0, 0) else WindowInsets.displayCutout

private inline val Context.deviceWidth: Int
    get() {
        val windowManager = ContextCompat.getSystemService(this, WindowManager::class.java) as WindowManager
        return if (Build.VERSION.SDK_INT >= 30) {
            val windowMetrics = windowManager.currentWindowMetrics
            val insets = windowMetrics.windowInsets.getInsetsIgnoringVisibility(
                android.view.WindowInsets.Type.systemBars()
            )
            windowMetrics.bounds.width() - insets.left - insets.right
        } else {
            val displayMetrics = DisplayMetrics()
            windowManager.defaultDisplay.getMetrics(displayMetrics)
            displayMetrics.widthPixels
        }
    }

private inline val Context.deviceHeight: Int
    get() {
        val windowManager = ContextCompat.getSystemService(this, WindowManager::class.java) as WindowManager
        return if (Build.VERSION.SDK_INT >= 30) {
            val windowMetrics = windowManager.currentWindowMetrics
            windowMetrics.bounds.height()
        } else {
            val displayMetrics = DisplayMetrics()
            windowManager.defaultDisplay.getMetrics(displayMetrics)
            displayMetrics.heightPixels
        }
    }