package org.michaelbel.movies.settings

import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import org.michaelbel.movies.analytics.Analytics
import org.michaelbel.movies.analytics.model.AnalyticsScreen
import org.michaelbel.movies.core.viewmodel.BaseViewModel
import org.michaelbel.movies.domain.interactor.SettingsInteractor
import org.michaelbel.movies.ui.SystemTheme

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val settingsInteractor: SettingsInteractor,
    analytics: Analytics
): BaseViewModel() {

    val themes: List<SystemTheme> = listOf(
        SystemTheme.NightNo,
        SystemTheme.NightYes,
        SystemTheme.FollowSystem
    )
    val currentTheme: Flow<SystemTheme> = settingsInteractor.currentTheme

    val dynamicColors: Flow<Boolean> = settingsInteractor.dynamicColors

    init {
        analytics.trackScreen(AnalyticsScreen.SETTINGS)
    }

    fun selectTheme(systemTheme: SystemTheme) = launch {
        settingsInteractor.selectTheme(systemTheme)
    }

    fun setDynamicColors(value: Boolean) = launch {
        settingsInteractor.setDynamicColors(value)
    }
}