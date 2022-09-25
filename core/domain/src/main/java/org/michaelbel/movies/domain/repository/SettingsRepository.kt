package org.michaelbel.movies.domain.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.michaelbel.movies.domain.datasource.ktx.PREFERENCE_THEME_KEY
import org.michaelbel.movies.domain.datasource.ktx.orDefaultTheme
import org.michaelbel.movies.ui.SystemTheme

class SettingsRepository @Inject constructor(
    private val dataStore: DataStore<Preferences>
) {

    val currentTheme: Flow<SystemTheme> = dataStore.data.map { preferences ->
        return@map SystemTheme.transform(preferences[PREFERENCE_THEME_KEY].orDefaultTheme())
    }

    suspend fun selectTheme(systemTheme: SystemTheme) {
        dataStore.edit { preferences ->
            preferences[PREFERENCE_THEME_KEY] = systemTheme.theme
        }
    }
}