package org.michaelbel.movies.common.theme

import androidx.appcompat.app.AppCompatDelegate
import org.michaelbel.movies.common.theme.exceptions.InvalidThemeException

sealed class AppTheme(
    val theme: Int
) {
    object NightNo: AppTheme(AppCompatDelegate.MODE_NIGHT_NO) {
        override fun toString(): String = "NightNo"
    }

    object NightYes: AppTheme(AppCompatDelegate.MODE_NIGHT_YES) {
        override fun toString(): String = "NightYes"
    }

    object FollowSystem: AppTheme(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM) {
        override fun toString(): String = "FollowSystem"
    }

    companion object {
        fun transform(theme: Int): AppTheme {
            return when (theme) {
                AppCompatDelegate.MODE_NIGHT_NO -> NightNo
                AppCompatDelegate.MODE_NIGHT_YES -> NightYes
                AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM -> FollowSystem
                else -> throw InvalidThemeException
            }
        }
    }
}