package org.michaelbel.movies.domain.repository

import kotlinx.coroutines.flow.Flow
import org.michaelbel.movies.ui.theme.SystemTheme

interface SettingsRepository {

    val currentTheme: Flow<SystemTheme>

    val dynamicColors: Flow<Boolean>

    suspend fun selectTheme(systemTheme: SystemTheme)

    suspend fun setDynamicColors(value: Boolean)
}