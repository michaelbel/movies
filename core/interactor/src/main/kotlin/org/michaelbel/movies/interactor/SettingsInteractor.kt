package org.michaelbel.movies.interactor

import androidx.compose.ui.unit.LayoutDirection
import kotlinx.coroutines.flow.Flow
import org.michaelbel.movies.common.appearance.FeedView
import org.michaelbel.movies.common.list.MovieList
import org.michaelbel.movies.common.theme.AppTheme
import org.michaelbel.movies.common.version.AppVersionData

interface SettingsInteractor {

    val currentTheme: Flow<AppTheme>

    val currentFeedView: Flow<FeedView>

    val currentMovieList: Flow<MovieList>

    val dynamicColors: Flow<Boolean>

    val layoutDirection: Flow<LayoutDirection>

    val isSettingsIconVisible: Flow<Boolean>

    val isPlayServicesAvailable: Flow<Boolean>

    val appVersionData: Flow<AppVersionData>

    suspend fun selectTheme(appTheme: AppTheme)

    suspend fun selectFeedView(feedView: FeedView)

    suspend fun selectMovieList(movieList: MovieList)

    suspend fun setDynamicColors(value: Boolean)

    suspend fun setRtlEnabled(value: Boolean)

    suspend fun fetchRemoteConfig()
}