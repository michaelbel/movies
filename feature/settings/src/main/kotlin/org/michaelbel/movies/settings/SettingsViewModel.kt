@file:Suppress("AnnotateVersionCheck")

package org.michaelbel.movies.settings

import android.os.Build
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.michaelbel.movies.common.viewmodel.BaseViewModel
import org.michaelbel.movies.domain.interactor.SettingsInteractor
import org.michaelbel.movies.domain.usecase.SelectThemeCase
import org.michaelbel.movies.ui.theme.model.SystemTheme

@HiltViewModel
internal class SettingsViewModel @Inject constructor(
    private val settingsInteractor: SettingsInteractor,
    private val selectThemeCase: SelectThemeCase
): BaseViewModel() {

    val isDynamicColorsFeatureEnabled: Boolean = Build.VERSION.SDK_INT >= 31

    val isPostNotificationsFeatureEnabled: Boolean = Build.VERSION.SDK_INT >= 33

    val themes: List<SystemTheme> = listOf(
        SystemTheme.NightNo,
        SystemTheme.NightYes,
        SystemTheme.FollowSystem
    )

    val currentTheme: StateFlow<SystemTheme> = settingsInteractor.currentTheme
        .stateIn(
            scope = this,
            started = SharingStarted.Lazily,
            initialValue = SystemTheme.FollowSystem
        )

    val dynamicColors: StateFlow<Boolean> = settingsInteractor.dynamicColors
        .stateIn(
            scope = this,
            started = SharingStarted.Lazily,
            initialValue = false
        )

    val isPlayServicesAvailable: StateFlow<Boolean> = settingsInteractor.isPlayServicesAvailable
        .stateIn(
            scope = this,
            started = SharingStarted.Lazily,
            initialValue = false
        )

    val isAppFromGooglePlay: StateFlow<Boolean> = settingsInteractor.isAppFromGooglePlay
        .stateIn(
            scope = this,
            started = SharingStarted.Lazily,
            initialValue = false
        )

    private val _areNotificationsEnabled: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val areNotificationsEnabled: StateFlow<Boolean> = _areNotificationsEnabled.asStateFlow()

    init {
        checkNotificationsEnabled()
    }

    fun selectTheme(systemTheme: SystemTheme) = launch {
        selectThemeCase(systemTheme)
    }

    fun setDynamicColors(value: Boolean) = launch {
        settingsInteractor.setDynamicColors(value)
    }

    fun checkNotificationsEnabled() {
        _areNotificationsEnabled.value = settingsInteractor.areNotificationsEnabled
    }
}