package org.michaelbel.movies.domain.datasource.ktx

import android.content.Context
import androidx.datastore.preferences.SharedPreferencesMigration
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore

private const val USER_PREFERENCES_NAME = "user_preferences"

internal val PREFERENCE_THEME_KEY = intPreferencesKey("theme")
internal val PREFERENCE_DYNAMIC_COLORS_KEY = booleanPreferencesKey("dynamic_colors")
internal val PREFERENCE_RTL_ENABLED_KEY = booleanPreferencesKey("rtl_enabled")
internal val PREFERENCE_NETWORK_REQUEST_DELAY = intPreferencesKey("network_request_delay")

internal val Context.dataStore by preferencesDataStore(
    name = USER_PREFERENCES_NAME,
    produceMigrations = { context ->
        listOf(SharedPreferencesMigration(context, USER_PREFERENCES_NAME))
    }
)