package org.michaelbel.movies.settings.ktx

import org.michaelbel.movies.settings_impl.R
import org.michaelbel.movies.ui.language.model.AppLanguage

internal val AppLanguage.languageTextRes: Int
    get() = when (this) {
        is AppLanguage.English -> R.string.settings_language_en
        is AppLanguage.Russian -> R.string.settings_language_ru
    }