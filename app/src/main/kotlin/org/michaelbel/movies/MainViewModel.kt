package org.michaelbel.movies

import android.os.Bundle
import androidx.compose.ui.unit.LayoutDirection
import androidx.navigation.NavDestination
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.michaelbel.movies.analytics.MoviesAnalytics
import org.michaelbel.movies.common.theme.AppTheme
import org.michaelbel.movies.common.viewmodel.BaseViewModel
import org.michaelbel.movies.domain.interactor.settings.SettingsInteractor
import javax.inject.Inject

@HiltViewModel
internal class MainViewModel @Inject constructor(
    private val settingsInteractor: SettingsInteractor,
    private val analytics: MoviesAnalytics
): BaseViewModel() {

    val currentTheme: StateFlow<AppTheme> = settingsInteractor.currentTheme
        .stateIn(
            scope = this,
            started = SharingStarted.Lazily,
            initialValue = AppTheme.FollowSystem
        )

    val dynamicColors: StateFlow<Boolean> = settingsInteractor.dynamicColors
        .stateIn(
            scope = this,
            started = SharingStarted.Lazily,
            initialValue = false
        )

    val layoutDirection: StateFlow<LayoutDirection> = settingsInteractor.layoutDirection
        .stateIn(
            scope = this,
            started = SharingStarted.Lazily,
            initialValue = LayoutDirection.Ltr
        )

    init {
        fetchRemoteConfig()
    }

    fun analyticsTrackDestination(destination: NavDestination, arguments: Bundle?) {
        analytics.trackDestination(destination.route, arguments)
    }

    private fun fetchRemoteConfig() = launch {
        settingsInteractor.fetchRemoteConfig()
    }
}