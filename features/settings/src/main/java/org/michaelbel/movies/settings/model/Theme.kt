package org.michaelbel.movies.settings.model

import androidx.appcompat.app.AppCompatDelegate

@JvmInline
value class Theme(
    val value: Int
) {
    companion object {
        const val THEME_DEFAULT: Int = AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
    }
}