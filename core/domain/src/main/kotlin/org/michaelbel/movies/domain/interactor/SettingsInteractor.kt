package org.michaelbel.movies.domain.interactor

import kotlinx.coroutines.flow.Flow
import org.michaelbel.movies.ui.SystemTheme

interface SettingsInteractor {

    val currentTheme: Flow<SystemTheme>

    val dynamicColors: Flow<Boolean>

    val areNotificationsEnabled: Boolean

    val isSettingsIconVisible: Flow<Boolean>

    suspend fun selectTheme(systemTheme: SystemTheme)

    suspend fun setDynamicColors(value: Boolean)
}