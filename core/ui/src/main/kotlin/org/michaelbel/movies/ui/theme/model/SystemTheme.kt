package org.michaelbel.movies.ui.theme.model

import androidx.appcompat.app.AppCompatDelegate
import org.michaelbel.movies.ui.theme.exceptions.InvalidThemeException

sealed class SystemTheme(val theme: Int) {
    object NightNo: SystemTheme(AppCompatDelegate.MODE_NIGHT_NO) {
        override fun toString(): String = "NightNo"
    }

    object NightYes: SystemTheme(AppCompatDelegate.MODE_NIGHT_YES) {
        override fun toString(): String = "NightYes"
    }

    object FollowSystem: SystemTheme(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM) {
        override fun toString(): String = "FollowSystem"
    }

    companion object {
        fun transform(theme: Int): SystemTheme {
            return when (theme) {
                AppCompatDelegate.MODE_NIGHT_NO -> NightNo
                AppCompatDelegate.MODE_NIGHT_YES -> NightYes
                AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM -> FollowSystem
                else -> throw InvalidThemeException
            }
        }
    }
}