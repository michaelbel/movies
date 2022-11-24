package org.michaelbel.movies.domain.usecase

import javax.inject.Inject
import org.michaelbel.movies.common.localization.LocaleController
import org.michaelbel.movies.ui.language.model.AppLanguage

class SelectLanguageCase @Inject constructor(
    private val localeController: LocaleController
) {
    suspend operator fun invoke(language: AppLanguage) {
        localeController.selectLanguage(language)
    }
}