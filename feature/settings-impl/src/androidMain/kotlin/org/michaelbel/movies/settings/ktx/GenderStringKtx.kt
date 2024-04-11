package org.michaelbel.movies.settings.ktx

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import org.michaelbel.movies.settings_impl.R

internal actual val SettingsGenderText: String
    @Composable get() = stringResource(R.string.settings_gender)