package org.michaelbel.movies.domain.usecase

import org.michaelbel.movies.common.dispatchers.SharedDispatchers
import org.michaelbel.movies.persistence.datastore.MoviesPreferences
import org.michaelbel.movies.persistence.datastore.TmdbPreferences
import org.michaelbel.movies.persistence.datastore.TraktPreferences

class ResetSettingsUseCase(
    private val preferences: MoviesPreferences,
    private val tmdbPreferences: TmdbPreferences,
    private val traktPreferences: TraktPreferences,
    dispatchers: SharedDispatchers
): UseCase<Unit, Unit>(dispatchers.io) {

    override suspend fun execute(params: Unit) {
        preferences.removeValues(
            MoviesPreferences.PreferenceKey.PreferenceThemeKey,
            MoviesPreferences.PreferenceKey.PreferenceFeedViewKey,
            MoviesPreferences.PreferenceKey.PreferenceDynamicColorsKey,
            MoviesPreferences.PreferenceKey.PreferencePaletteColorsKey,
            MoviesPreferences.PreferenceKey.PreferencePaletteKey,
            MoviesPreferences.PreferenceKey.PreferenceSeedColorKey,
            MoviesPreferences.PreferenceKey.PreferenceBiometricKey,
            MoviesPreferences.PreferenceKey.PreferenceProviderKey,
            MoviesPreferences.PreferenceKey.PreferenceScreenshotBlockKey
        )
        tmdbPreferences.removeValue(TmdbPreferences.PreferenceKey.PreferenceMovieListKey)
        traktPreferences.removeValue(TraktPreferences.PreferenceKey.PreferenceTraktListKey)
    }
}
