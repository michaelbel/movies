package org.michaelbel.movies.ui.ktx

import android.app.Activity
import android.os.Build

private val screenCaptureCallback: Any
    get() {
        return if (Build.VERSION.SDK_INT >= 34) {
            Activity.ScreenCaptureCallback {}
        } else {
            Unit
        }
    }

fun Activity.supportRegisterScreenCaptureCallback() {
    if (Build.VERSION.SDK_INT >= 34) {
        registerScreenCaptureCallback(mainExecutor, screenCaptureCallback as Activity.ScreenCaptureCallback)
    }
}

fun Activity.supportUnregisterScreenCaptureCallback() {
    if (Build.VERSION.SDK_INT >= 34) {
        unregisterScreenCaptureCallback(screenCaptureCallback as Activity.ScreenCaptureCallback)
    }
}