package org.michaelbel.movies.repository.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import org.michaelbel.movies.common.ThemeData
import org.michaelbel.movies.common.appearance.FeedView
import org.michaelbel.movies.common.list.MovieList
import org.michaelbel.movies.common.theme.AppTheme
import org.michaelbel.movies.persistence.database.ktx.orEmpty
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
        enabled.orEmpty()
    }

    override val isScreenshotBlockEnabled: Flow<Boolean> = preferences.isScreenshotBlockEnabledFlow.map { enabled ->
        enabled.orEmpty()
    }

    override suspend fun isBiometricEnabledAsync(): Boolean {
        return preferences.isBiometricEnabledAsync()
    }

    override suspend fun selectTheme(
        appTheme: AppTheme
    ) {
        preferences.setValue(MoviesPreferences.PreferenceKey.PreferenceThemeKey, appTheme.toString())
    }

    override suspend fun selectFeedView(
        feedView: FeedView
    ) {
        preferences.setValue(MoviesPreferences.PreferenceKey.PreferenceFeedViewKey, feedView.toString())
    }

    override suspend fun selectMovieList(
        movieList: MovieList
    ) {
        preferences.setValue(MoviesPreferences.PreferenceKey.PreferenceMovieListKey, movieList.toString())
    }

    override suspend fun setDynamicColors(
        value: Boolean
    ) {
        preferences.setValue(MoviesPreferences.PreferenceKey.PreferenceDynamicColorsKey, value)
    }

    override suspend fun setPaletteKey(
        paletteKey: Int
    ) {
        preferences.setValue(MoviesPreferences.PreferenceKey.PreferencePaletteKey, paletteKey)
    }

    override suspend fun setSeedColor(
        seedColor: Int
    ) {
        preferences.setValue(MoviesPreferences.PreferenceKey.PreferenceSeedColorKey, seedColor)
    }

    override suspend fun setBiometricEnabled(
        enabled: Boolean
    ) {
        preferences.setValue(MoviesPreferences.PreferenceKey.PreferenceBiometricKey, enabled)
    }

    override suspend fun setScreenshotBlockEnabled(
        enabled: Boolean
    ) {
        preferences.setValue(MoviesPreferences.PreferenceKey.PreferenceScreenshotBlockKey, enabled)
    }
}