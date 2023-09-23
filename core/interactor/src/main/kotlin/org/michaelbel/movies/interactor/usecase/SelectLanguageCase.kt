package org.michaelbel.movies.interactor.usecase

import org.michaelbel.movies.common.localization.LocaleController
import org.michaelbel.movies.common.localization.model.AppLanguage
import javax.inject.Inject

class SelectLanguageCase @Inject constructor(
    private val localeController: LocaleController
) {
    suspend operator fun invoke(language: AppLanguage) {
        localeController.selectLanguage(language)
    }
}