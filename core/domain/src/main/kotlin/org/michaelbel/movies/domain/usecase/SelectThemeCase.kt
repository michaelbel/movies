package org.michaelbel.movies.domain.usecase

import javax.inject.Inject
import org.michaelbel.movies.domain.interactor.settings.SettingsInteractor
import org.michaelbel.movies.ui.theme.model.AppTheme

class SelectThemeCase @Inject constructor(
    private val settingsInteractor: SettingsInteractor
) {
    suspend operator fun invoke(appTheme: AppTheme) {
        settingsInteractor.selectTheme(appTheme)
    }
}