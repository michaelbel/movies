package org.michaelbel.movies.ui.ktx

import android.app.Activity
import android.os.Build

fun Activity.supportRegisterScreenCaptureCallback(screenCaptureCallback: Any) {
    if (Build.VERSION.SDK_INT >= 34) {
        registerScreenCaptureCallback(mainExecutor, screenCaptureCallback as Activity.ScreenCaptureCallback)
    }
}

fun Activity.supportUnregisterScreenCaptureCallback(screenCaptureCallback: Any) {
    if (Build.VERSION.SDK_INT >= 34) {
        try {
            unregisterScreenCaptureCallback(screenCaptureCallback as Activity.ScreenCaptureCallback)
        } catch (_: Exception) {}
    }
}