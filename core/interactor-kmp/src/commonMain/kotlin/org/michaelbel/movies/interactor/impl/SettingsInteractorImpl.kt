package org.michaelbel.movies.interactor.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import org.michaelbel.movies.analytics.MoviesAnalytics
import org.michaelbel.movies.analytics.event.ChangeDynamicColorsEvent
import org.michaelbel.movies.analytics.event.SelectFeedViewEvent
import org.michaelbel.movies.analytics.event.SelectMovieListEvent
import org.michaelbel.movies.analytics.event.SelectThemeEvent
import org.michaelbel.movies.common.ThemeData
import org.michaelbel.movies.common.appearance.FeedView
import org.michaelbel.movies.common.dispatchers.MoviesDispatchers
import org.michaelbel.movies.common.list.MovieList
import org.michaelbel.movies.common.theme.AppTheme
import org.michaelbel.movies.interactor.SettingsInteractor
import org.michaelbel.movies.repository.SettingsRepository

internal class SettingsInteractorImpl(
    private val dispatchers: MoviesDispatchers,
    private val settingsRepository: SettingsRepository,
    private val analytics: MoviesAnalytics
): SettingsInteractor {

    override val currentTheme: Flow<AppTheme> = settingsRepository.currentTheme

    override val currentFeedView: Flow<FeedView> = settingsRepository.currentFeedView

    override val currentMovieList: Flow<MovieList> = settingsRepository.currentMovieList

    override val themeData: Flow<ThemeData> = settingsRepository.themeData

    override val isBiometricEnabled: Flow<Boolean> = settingsRepository.isBiometricEnabled

    override val isScreenshotBlockEnabled: Flow<Boolean> = settingsRepository.isScreenshotBlockEnabled

    override suspend fun isBiometricEnabledAsync(): Boolean {
        return settingsRepository.isBiometricEnabledAsync()
    }

    override suspend fun selectTheme(
        appTheme: AppTheme
    ) {
        withContext(dispatchers.main) {
            settingsRepository.selectTheme(appTheme)
            analytics.logEvent(SelectThemeEvent(appTheme.toString()))
        }
    }

    override suspend fun selectFeedView(
        feedView: FeedView
    ) {
        withContext(dispatchers.main) {
            settingsRepository.selectFeedView(feedView)
            analytics.logEvent(SelectFeedViewEvent(feedView.toString()))
        }
    }

    override suspend fun selectMovieList(
        movieList: MovieList
    ) {
        withContext(dispatchers.main) {
            settingsRepository.selectMovieList(movieList)
            analytics.logEvent(SelectMovieListEvent(movieList.toString()))
        }
    }

    override suspend fun setDynamicColors(
        value: Boolean
    ) {
        withContext(dispatchers.main) {
            settingsRepository.setDynamicColors(value)
            analytics.logEvent(ChangeDynamicColorsEvent(value))
        }
    }

    override suspend fun setPaletteKey(
        paletteKey: Int
    ) {
        withContext(dispatchers.main) {
            settingsRepository.setPaletteKey(paletteKey)
        }
    }

    override suspend fun setSeedColor(
        seedColor: Int
    ) {
        withContext(dispatchers.main) {
            settingsRepository.setSeedColor(seedColor)
        }
    }

    override suspend fun setBiometricEnabled(
        enabled: Boolean
    ) {
        withContext(dispatchers.main) {
            settingsRepository.setBiometricEnabled(enabled)
        }
    }

    override suspend fun setScreenshotBlockEnabled(
        enabled: Boolean
    ) {
        withContext(dispatchers.main) {
            settingsRepository.setScreenshotBlockEnabled(enabled)
        }
    }
}