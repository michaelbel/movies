package org.michaelbel.movies.ui.ktx

import android.view.Window
import android.view.WindowManager

fun Window.setScreenshotBlockEnabled(enabled: Boolean) {
    if (enabled) {
        setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE)
    } else {
        clearFlags(WindowManager.LayoutParams.FLAG_SECURE)
    }
}