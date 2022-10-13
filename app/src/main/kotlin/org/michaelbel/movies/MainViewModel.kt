package org.michaelbel.movies

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.michaelbel.movies.domain.interactor.SettingsInteractor
import org.michaelbel.movies.ui.theme.SystemTheme
import timber.log.Timber

@HiltViewModel
class MainViewModel @Inject constructor(
    private val settingsInteractor: SettingsInteractor
): ViewModel() {

    val currentTheme: StateFlow<SystemTheme> = settingsInteractor.currentTheme
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Lazily,
            initialValue = SystemTheme.FollowSystem
        )

    val dynamicColors: StateFlow<Boolean> = settingsInteractor.dynamicColors
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Lazily,
            initialValue = false
        )

    init {
        viewModelScope.launch {
            settingsInteractor.fetchRemoteConfig()
        }
    }
}