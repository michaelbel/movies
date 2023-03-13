package org.michaelbel.movies.domain.usecase

import org.michaelbel.movies.common.theme.AppTheme
import org.michaelbel.movies.domain.interactor.settings.SettingsInteractor
import javax.inject.Inject

class SelectThemeCase @Inject constructor(
    private val settingsInteractor: SettingsInteractor
) {
    suspend operator fun invoke(appTheme: AppTheme) {
        settingsInteractor.selectTheme(appTheme)
    }
}