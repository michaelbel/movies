package org.michaelbel.movies.domain.interactor

import androidx.compose.ui.unit.LayoutDirection
import kotlinx.coroutines.flow.Flow
import org.michaelbel.movies.ui.theme.model.AppTheme

interface SettingsInteractor {

    val currentTheme: Flow<AppTheme>

    val dynamicColors: Flow<Boolean>

    val layoutDirection: Flow<LayoutDirection>

    val areNotificationsEnabled: Boolean

    val isSettingsIconVisible: Flow<Boolean>

    val isPlayServicesAvailable: Flow<Boolean>

    val isAppFromGooglePlay: Flow<Boolean>

    suspend fun selectTheme(theme: AppTheme)

    suspend fun setDynamicColors(value: Boolean)

    suspend fun setRtlEnabled(value: Boolean)

    suspend fun fetchRemoteConfig()
}