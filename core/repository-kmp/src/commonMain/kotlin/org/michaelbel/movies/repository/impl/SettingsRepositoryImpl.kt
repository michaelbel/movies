package org.michaelbel.movies.repository.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.michaelbel.movies.common.appearance.FeedView
import org.michaelbel.movies.common.list.MovieList
import org.michaelbel.movies.common.theme.AppTheme
import org.michaelbel.movies.persistence.datastore.MoviesPreferences
import org.michaelbel.movies.repository.SettingsRepository
import org.michaelbel.movies.repository.ktx.defaultDynamicColorsEnabled

internal class SettingsRepositoryImpl(
    private val preferences: MoviesPreferences
): SettingsRepository {

    override val currentTheme: Flow<AppTheme> = preferences.themeFlow.map { name ->
        AppTheme.transform(name ?: AppTheme.FollowSystem.toString())
    }

    override val currentFeedView: Flow<FeedView> = preferences.feedViewFlow.map { name ->
        FeedView.transform(name ?: FeedView.FeedList.toString())
    }

    override val currentMovieList: Flow<MovieList> = preferences.movieListFlow.map { className ->
        MovieList.transform(className ?: MovieList.NowPlaying.toString())
    }

    override val dynamicColors: Flow<Boolean> = preferences.isDynamicColorsFlow.map { isDynamicColors ->
        isDynamicColors ?: defaultDynamicColorsEnabled
    }

    override val isBiometricEnabled: Flow<Boolean> = preferences.isBiometricEnabled.map { enabled ->
        enabled ?: false
    }

    override suspend fun isBiometricEnabledAsync(): Boolean {
        return preferences.isBiometricEnabledAsync()
    }

    override suspend fun selectTheme(appTheme: AppTheme) {
        preferences.setTheme(appTheme.toString())
    }

    override suspend fun selectFeedView(feedView: FeedView) {
        preferences.setFeedView(feedView.toString())
    }

    override suspend fun selectMovieList(movieList: MovieList) {
        preferences.setMovieList(movieList.toString())
    }

    override suspend fun setDynamicColors(value: Boolean) {
        preferences.setDynamicColors(value)
    }

    override suspend fun setBiometricEnabled(enabled: Boolean) {
        preferences.setBiometricEnabled(enabled)
    }
}