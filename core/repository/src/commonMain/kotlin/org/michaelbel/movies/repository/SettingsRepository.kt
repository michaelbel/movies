package org.michaelbel.movies.repository

import kotlinx.coroutines.flow.Flow
import org.michaelbel.movies.common.ThemeData
import org.michaelbel.movies.common.appearance.FeedView
import org.michaelbel.movies.common.list.MovieList
import org.michaelbel.movies.common.theme.AppTheme

interface SettingsRepository {

    val currentTheme: Flow<AppTheme>

    val currentFeedView: Flow<FeedView>

    val currentMovieList: Flow<MovieList>

    val themeData: Flow<ThemeData>

    val isBiometricEnabled: Flow<Boolean>

    val isScreenshotBlockEnabled: Flow<Boolean>

    suspend fun isBiometricEnabledAsync(): Boolean

    suspend fun selectTheme(
        appTheme: AppTheme
    )

    suspend fun selectFeedView(
        feedView: FeedView
    )

    suspend fun selectMovieList(
        movieList: MovieList
    )

    suspend fun setDynamicColors(
        value: Boolean
    )

    suspend fun setPaletteKey(
        paletteKey: Int
    )

    suspend fun setSeedColor(
        seedColor: Int
    )

    suspend fun setBiometricEnabled(
        enabled: Boolean
    )

    suspend fun setScreenshotBlockEnabled(
        enabled: Boolean
    )
}