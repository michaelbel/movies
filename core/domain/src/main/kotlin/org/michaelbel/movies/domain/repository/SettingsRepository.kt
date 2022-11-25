package org.michaelbel.movies.domain.repository

import kotlinx.coroutines.flow.Flow
import org.michaelbel.movies.ui.theme.model.AppTheme

interface SettingsRepository {

    val currentTheme: Flow<AppTheme>

    val dynamicColors: Flow<Boolean>

    val rtlEnabled: Flow<Boolean>

    suspend fun selectTheme(theme: AppTheme)

    suspend fun setDynamicColors(value: Boolean)

    suspend fun setRtlEnabled(value: Boolean)
}