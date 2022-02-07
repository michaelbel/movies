package org.michaelbel.moviemade.ui.main

import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    dataStore: DataStore<Preferences>
): ViewModel() {

    val currentTheme: Flow<Int> = dataStore.data.map { preferences ->
        return@map preferences[THEME_KEY] ?: AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
    }

    private companion object {
        private val THEME_KEY = intPreferencesKey("theme")
    }
}