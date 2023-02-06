package org.michaelbel.movies.settings

import android.os.Build
import androidx.compose.ui.unit.LayoutDirection
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
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
import org.michaelbel.movies.domain.usecase.SelectLanguageCase
import org.michaelbel.movies.domain.usecase.SelectThemeCase
import org.michaelbel.movies.ui.language.model.AppLanguage
import org.michaelbel.movies.ui.theme.model.AppTheme
import org.michaelbel.movies.ui.version.AppVersionData

@HiltViewModel
internal class SettingsViewModel @Inject constructor(
    private val settingsInteractor: SettingsInteractor,
    private val selectLanguageCase: SelectLanguageCase,
    private val selectThemeCase: SelectThemeCase
): BaseViewModel(), DefaultLifecycleObserver {

    val isDynamicColorsFeatureEnabled: Boolean = Build.VERSION.SDK_INT >= 31

    val isPostNotificationsFeatureEnabled: Boolean = Build.VERSION.SDK_INT >= 33

    val languages: List<AppLanguage> = listOf(
        AppLanguage.English,
        AppLanguage.Russian
    )

    val themes: List<AppTheme> = listOf(
        AppTheme.NightNo,
        AppTheme.NightYes,
        AppTheme.FollowSystem
    )

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

    val networkRequestDelay: StateFlow<Int> = settingsInteractor.networkRequestDelay
        .stateIn(
            scope = this,
            started = SharingStarted.Lazily,
            initialValue = 0
        )

    val appVersionData: StateFlow<AppVersionData> = settingsInteractor.appVersionData
        .stateIn(
            scope = this,
            started = SharingStarted.Lazily,
            initialValue = AppVersionData.None
        )

    private val _areNotificationsEnabled: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val areNotificationsEnabled: StateFlow<Boolean> = _areNotificationsEnabled.asStateFlow()

    init {
        checkNotificationsEnabled()
    }

    override fun onResume(owner: LifecycleOwner) {
        super.onResume(owner)
        checkNotificationsEnabled()
    }

    fun selectLanguage(language: AppLanguage) = launch {
        selectLanguageCase(language)
    }

    fun selectTheme(theme: AppTheme) = launch {
        selectThemeCase(theme)
    }

    fun setDynamicColors(value: Boolean) = launch {
        settingsInteractor.setDynamicColors(value)
    }

    fun setRtlEnabled(value: Boolean) = launch {
        settingsInteractor.setRtlEnabled(value)
    }

    fun setNetworkRequestDelay(value: Int) = launch {
        settingsInteractor.setNetworkRequestDelay(value)
    }

    fun checkNotificationsEnabled() {
        _areNotificationsEnabled.value = settingsInteractor.areNotificationsEnabled
    }
}