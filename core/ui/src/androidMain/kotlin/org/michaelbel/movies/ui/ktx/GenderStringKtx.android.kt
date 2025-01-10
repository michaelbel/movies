package org.michaelbel.movies.ui.ktx

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import org.michaelbel.movies.ui.R

actual val SettingsGenderText: String
    @Composable get() = stringResource(R.string.settings_gender)