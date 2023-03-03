package org.michaelbel.movies

import android.os.Bundle
import androidx.compose.ui.unit.LayoutDirection
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavDestination
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.michaelbel.movies.analytics.MoviesAnalytics
import org.michaelbel.movies.domain.interactor.settings.SettingsInteractor
import org.michaelbel.movies.ui.theme.model.AppTheme

@HiltViewModel
internal class MainViewModel @Inject constructor(
    private val settingsInteractor: SettingsInteractor,
    private val analytics: MoviesAnalytics
): ViewModel() {

    val currentTheme: StateFlow<AppTheme> = settingsInteractor.currentTheme
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Lazily,
            initialValue = AppTheme.FollowSystem
        )

    val dynamicColors: StateFlow<Boolean> = settingsInteractor.dynamicColors
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Lazily,
            initialValue = false
        )

    val layoutDirection: StateFlow<LayoutDirection> = settingsInteractor.layoutDirection
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Lazily,
            initialValue = LayoutDirection.Ltr
        )

    init {
        viewModelScope.launch {
            settingsInteractor.fetchRemoteConfig()
        }
    }

    fun analyticsTrackDestination(destination: NavDestination, arguments: Bundle?) {
        analytics.trackDestination(destination.route, arguments)
    }
}