package org.michaelbel.movies.common.theme

import androidx.appcompat.app.AppCompatDelegate
import org.michaelbel.movies.common.theme.exceptions.InvalidThemeException

sealed class AppTheme(
    val theme: Int
) {
    data object NightNo: AppTheme(AppCompatDelegate.MODE_NIGHT_NO)

    data object NightYes: AppTheme(AppCompatDelegate.MODE_NIGHT_YES)

    data object FollowSystem: AppTheme(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)

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