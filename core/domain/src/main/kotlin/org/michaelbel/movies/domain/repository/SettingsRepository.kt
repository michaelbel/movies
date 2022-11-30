package org.michaelbel.movies.domain.repository

import androidx.compose.ui.unit.LayoutDirection
import kotlinx.coroutines.flow.Flow
import org.michaelbel.movies.ui.theme.model.AppTheme

interface SettingsRepository {

    val currentTheme: Flow<AppTheme>

    val dynamicColors: Flow<Boolean>

    val layoutDirection: Flow<LayoutDirection>

    val networkRequestDelay: Flow<Int>

    suspend fun networkRequestDelay(): Long

    suspend fun selectTheme(theme: AppTheme)

    suspend fun setDynamicColors(value: Boolean)

    suspend fun setRtlEnabled(value: Boolean)

    suspend fun setNetworkRequestDelay(value: Int)
}