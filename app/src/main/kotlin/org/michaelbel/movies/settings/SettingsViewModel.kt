package org.michaelbel.movies.settings

import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val dataStore: DataStore<Preferences>
): ViewModel() {

    val themes = listOf(
        AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM,
        AppCompatDelegate.MODE_NIGHT_NO,
        AppCompatDelegate.MODE_NIGHT_YES
    )

    val currentTheme: Flow<Int> = dataStore.data.map { preferences ->
        return@map preferences[THEME_KEY] ?: themes.first()
    }

    fun selectTheme(theme: Int) = viewModelScope.launch {
        dataStore.edit { preferences ->
            preferences[THEME_KEY] = theme
        }
    }

    private companion object {
        private val THEME_KEY = intPreferencesKey("theme")
    }
}