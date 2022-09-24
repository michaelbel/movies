package org.michaelbel.movies.domain

import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.intPreferencesKey
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SettingsRepository @Inject constructor(
    dataStore: DataStore<Preferences>
) {

    val currentTheme: Flow<Int> = dataStore.data.map { preferences ->
        return@map preferences[THEME_KEY] ?: AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
    }

    private companion object {
        private val THEME_KEY = intPreferencesKey("theme")
    }
}