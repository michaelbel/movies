package org.michaelbel.movies.settings.ktx

import org.michaelbel.movies.settings.R
import org.michaelbel.movies.ui.theme.model.AppTheme

internal val AppTheme.themeTextRes: Int
    get() = when (this) {
        is AppTheme.NightNo -> R.string.settings_theme_light
        is AppTheme.NightYes -> R.string.settings_theme_dark
        is AppTheme.FollowSystem -> R.string.settings_theme_system
    }