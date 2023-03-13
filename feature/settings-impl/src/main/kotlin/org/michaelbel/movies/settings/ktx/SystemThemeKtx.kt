package org.michaelbel.movies.settings.ktx

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import org.michaelbel.movies.common.theme.AppTheme
import org.michaelbel.movies.settings_impl.R

internal val AppTheme.themeText: String
    @Composable get() = when (this) {
        is AppTheme.NightNo -> stringResource(R.string.settings_theme_light)
        is AppTheme.NightYes -> stringResource(R.string.settings_theme_dark)
        is AppTheme.FollowSystem -> stringResource(R.string.settings_theme_system)
    }