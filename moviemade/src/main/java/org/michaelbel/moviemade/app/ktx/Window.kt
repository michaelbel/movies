package org.michaelbel.moviemade.app.ktx

import android.view.Window
import androidx.core.view.WindowInsetsControllerCompat

fun Window.setLightStatusBar(state: Boolean) {
    val windowInsetControllerCompat = WindowInsetsControllerCompat(this, decorView)
    windowInsetControllerCompat.isAppearanceLightStatusBars = state
}