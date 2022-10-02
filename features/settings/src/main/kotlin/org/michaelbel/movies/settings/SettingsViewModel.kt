package org.michaelbel.movies.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import org.michaelbel.movies.analytics.Analytics
import org.michaelbel.movies.analytics.model.AnalyticsScreen
import org.michaelbel.movies.domain.interactor.SettingsInteractor
import org.michaelbel.movies.ui.SystemTheme

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val settingsInteractor: SettingsInteractor,
    analytics: Analytics
): ViewModel() {

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

    fun selectTheme(systemTheme: SystemTheme) = viewModelScope.launch {
        settingsInteractor.selectTheme(systemTheme)
    }

    fun setDynamicColors(value: Boolean) = viewModelScope.launch {
        settingsInteractor.setDynamicColors(value)
    }
}