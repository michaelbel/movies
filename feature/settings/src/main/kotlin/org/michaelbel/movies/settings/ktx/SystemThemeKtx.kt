package org.michaelbel.movies.settings.ktx

import org.michaelbel.movies.settings.R
import org.michaelbel.movies.ui.theme.model.SystemTheme

internal val SystemTheme.themeTextRes: Int
    get() = when (this) {
        is SystemTheme.NightNo -> R.string.settings_theme_light
        is SystemTheme.NightYes -> R.string.settings_theme_dark
        is SystemTheme.FollowSystem -> R.string.settings_theme_system
    }