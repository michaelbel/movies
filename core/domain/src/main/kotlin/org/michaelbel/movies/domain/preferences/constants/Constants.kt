package org.michaelbel.movies.domain.preferences.constants

import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey

internal const val USER_PREFERENCES_NAME = "user_preferences"

internal val PREFERENCE_THEME_KEY: Preferences.Key<Int> = intPreferencesKey("theme")
internal val PREFERENCE_DYNAMIC_COLORS_KEY: Preferences.Key<Boolean> = booleanPreferencesKey("dynamic_colors")
internal val PREFERENCE_RTL_ENABLED_KEY: Preferences.Key<Boolean> = booleanPreferencesKey("rtl_enabled")
internal val PREFERENCE_NETWORK_REQUEST_DELAY: Preferences.Key<Int> = intPreferencesKey("network_request_delay")