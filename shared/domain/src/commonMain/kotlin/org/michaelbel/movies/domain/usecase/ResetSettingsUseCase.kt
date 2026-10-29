package org.michaelbel.movies.domain.usecase

import org.michaelbel.movies.common.dispatchers.SharedDispatchers
import org.michaelbel.movies.persistence.datastore.MoviesPreferences

class ResetSettingsUseCase(
    private val preferences: MoviesPreferences,
    dispatchers: SharedDispatchers
): UseCase<Unit, Unit>(dispatchers.io) {

    override suspend fun execute(params: Unit) {
        preferences.removeValues(
            MoviesPreferences.PreferenceKey.PreferenceThemeKey,
            MoviesPreferences.PreferenceKey.PreferenceFeedViewKey,
            MoviesPreferences.PreferenceKey.PreferenceMovieListKey,
            MoviesPreferences.PreferenceKey.PreferenceDynamicColorsKey,
            MoviesPreferences.PreferenceKey.PreferencePaletteColorsKey,
            MoviesPreferences.PreferenceKey.PreferencePaletteKey,
            MoviesPreferences.PreferenceKey.PreferenceSeedColorKey,
            MoviesPreferences.PreferenceKey.PreferenceBiometricKey,
            MoviesPreferences.PreferenceKey.PreferenceProviderKey,
            MoviesPreferences.PreferenceKey.PreferenceScreenshotBlockKey
        )
    }
}
