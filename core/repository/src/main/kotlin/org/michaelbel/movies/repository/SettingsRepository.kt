package org.michaelbel.movies.repository

import androidx.compose.ui.unit.LayoutDirection
import kotlinx.coroutines.flow.Flow
import org.michaelbel.movies.common.appearance.FeedView
import org.michaelbel.movies.common.theme.AppTheme
import org.michaelbel.movies.common.version.AppVersionData

interface SettingsRepository {

    val currentTheme: Flow<AppTheme>

    val currentFeedView: Flow<FeedView>

    val dynamicColors: Flow<Boolean>

    val layoutDirection: Flow<LayoutDirection>

    val appVersionData: Flow<AppVersionData>

    suspend fun selectTheme(appTheme: AppTheme)

    suspend fun selectFeedView(feedView: FeedView)

    suspend fun setDynamicColors(value: Boolean)

    suspend fun setRtlEnabled(value: Boolean)
}