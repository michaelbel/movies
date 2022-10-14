package org.michaelbel.movies.domain.usecase

import javax.inject.Inject
import org.michaelbel.movies.domain.interactor.SettingsInteractor
import org.michaelbel.movies.ui.theme.SystemTheme

class SelectThemeCase @Inject constructor(
    private val settingsInteractor: SettingsInteractor
) {
    suspend operator fun invoke(systemTheme: SystemTheme) {
        settingsInteractor.selectTheme(systemTheme)
    }
}