package org.michaelbel.movies.domain.datasource.ktx

import android.content.Context
import androidx.datastore.preferences.SharedPreferencesMigration
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore

private const val USER_PREFERENCES_NAME = "user_preferences"

val PREFERENCE_THEME_KEY = intPreferencesKey("theme")
val PREFERENCE_DYNAMIC_COLORS_KEY = booleanPreferencesKey("dynamic_colors")
val PREFERENCE_RTL_ENABLED_KEY = booleanPreferencesKey("rtl_enabled")

val Context.dataStore by preferencesDataStore(
    name = USER_PREFERENCES_NAME,
    produceMigrations = { context ->
        listOf(SharedPreferencesMigration(context, USER_PREFERENCES_NAME))
    }
)