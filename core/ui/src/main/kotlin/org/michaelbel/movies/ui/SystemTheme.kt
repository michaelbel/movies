package org.michaelbel.movies.ui

import androidx.appcompat.app.AppCompatDelegate
import org.michaelbel.movies.ui.exceptions.InvalidThemeException

sealed class SystemTheme(val theme: Int) {
    object NightNo: SystemTheme(AppCompatDelegate.MODE_NIGHT_NO)
    object NightYes: SystemTheme(AppCompatDelegate.MODE_NIGHT_YES)
    object FollowSystem: SystemTheme(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)

    companion object {
        fun transform(theme: Int): SystemTheme {
            return when (theme) {
                AppCompatDelegate.MODE_NIGHT_NO -> NightNo
                AppCompatDelegate.MODE_NIGHT_YES -> NightYes
                AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM -> FollowSystem
                else -> throw InvalidThemeException()
            }
        }
    }
}