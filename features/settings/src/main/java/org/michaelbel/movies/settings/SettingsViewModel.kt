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
import org.michaelbel.movies.settings.model.Theme

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val dataStore: DataStore<Preferences>
): ViewModel() {

    val themes: List<Theme> = listOf(
        Theme(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM),
        Theme(AppCompatDelegate.MODE_NIGHT_NO),
        Theme(AppCompatDelegate.MODE_NIGHT_YES)
    )

    val currentTheme: Flow<Theme> = dataStore.data.map { preferences ->
        return@map Theme(preferences[THEME_KEY] ?: Theme.THEME_DEFAULT)
    }

    fun selectTheme(theme: Theme) = viewModelScope.launch {
        dataStore.edit { preferences ->
            preferences[THEME_KEY] = theme.value
        }
    }

    private companion object {
        private val THEME_KEY = intPreferencesKey("theme")
    }
}