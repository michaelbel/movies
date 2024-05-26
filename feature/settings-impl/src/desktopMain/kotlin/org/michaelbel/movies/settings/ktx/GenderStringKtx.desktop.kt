@file:OptIn(ExperimentalResourceApi::class)

package org.michaelbel.movies.settings.ktx

import androidx.compose.runtime.Composable
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.stringResource
import org.michaelbel.movies.ui.strings.MoviesStrings

internal actual val SettingsGenderText: String
    @Composable get() = stringResource(MoviesStrings.settings_gender)