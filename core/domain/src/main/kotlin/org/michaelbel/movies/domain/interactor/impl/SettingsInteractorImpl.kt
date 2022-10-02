package org.michaelbel.movies.domain.interactor.impl

import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import org.michaelbel.movies.analytics.Analytics
import org.michaelbel.movies.analytics.event.ChangeDynamicColorsEvent
import org.michaelbel.movies.analytics.event.SelectThemeEvent
import org.michaelbel.movies.domain.interactor.SettingsInteractor
import org.michaelbel.movies.domain.repository.SettingsRepository
import org.michaelbel.movies.ui.SystemTheme

class SettingsInteractorImpl @Inject constructor(
    private val settingsRepository: SettingsRepository,
    private val analytics: Analytics
): SettingsInteractor {

    override val currentTheme: Flow<SystemTheme> = settingsRepository.currentTheme

    override val dynamicColors: Flow<Boolean> = settingsRepository.dynamicColors

    override suspend fun selectTheme(systemTheme: SystemTheme) {
        settingsRepository.selectTheme(systemTheme)
        analytics.logEvent(SelectThemeEvent(systemTheme.toString()))
    }

    override suspend fun setDynamicColors(value: Boolean) {
        settingsRepository.setDynamicColors(value)
        analytics.logEvent(ChangeDynamicColorsEvent(value))
    }
}