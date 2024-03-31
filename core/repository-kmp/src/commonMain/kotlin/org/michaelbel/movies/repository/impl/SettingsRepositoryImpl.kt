package org.michaelbel.movies.repository.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import org.michaelbel.movies.common.ThemeData
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
        MovieList.transform(className ?: MovieList.NowPlaying().toString())
    }

    override val themeData: Flow<ThemeData>
        get() {
            return combine(
                preferences.themeFlow,
                preferences.isDynamicColorsFlow,
                preferences.paletteKeyFlow,
                preferences.seedColorFlow
            ) { name, dynamicColors, paletteKey, seedColor ->
                ThemeData(
                    appTheme = AppTheme.transform(name ?: AppTheme.FollowSystem.toString()),
                    dynamicColors = dynamicColors ?: defaultDynamicColorsEnabled,
                    paletteKey = paletteKey ?: ThemeData.STYLE_TONAL_SPOT,
                    seedColor = seedColor ?: ThemeData.DEFAULT_SEED_COLOR
                )
            }
        }

    override val isBiometricEnabled: Flow<Boolean> = preferences.isBiometricEnabledFlow.map { enabled ->
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

    override suspend fun setPaletteKey(paletteKey: Int) {
        preferences.setPaletteKey(paletteKey)
    }

    override suspend fun setSeedColor(seedColor: Int) {
        preferences.setSeedColor(seedColor)
    }

    override suspend fun setBiometricEnabled(enabled: Boolean) {
        preferences.setBiometricEnabled(enabled)
    }
}