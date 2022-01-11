package org.michaelbel.moviemade

import android.content.Context
import androidx.appcompat.app.AppCompatDelegate

val Context.isDarkTheme: Boolean
    get() {
        return when (AppCompatDelegate.getDefaultNightMode()) {
            AppCompatDelegate.MODE_NIGHT_YES -> true
            AppCompatDelegate.MODE_NIGHT_NO -> false
            AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM -> {
                resources.getBoolean(R.bool.isDarkTheme)
            }
            else -> resources.getBoolean(R.bool.isDarkTheme)
        }
    }