package org.michaelbel.movies.settings.ktx

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import org.michaelbel.movies.settings_impl.R
import org.michaelbel.movies.ui.language.model.AppLanguage

internal val AppLanguage.languageText: String
    @Composable get() = when (this) {
        is AppLanguage.English -> stringResource(R.string.settings_language_en)
        is AppLanguage.Russian -> stringResource(R.string.settings_language_ru)
    }