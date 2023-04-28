package org.michaelbel.movies.domain.preferences.constants

import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey

internal const val USER_PREFERENCES_NAME = "user_preferences"

internal val PREFERENCE_THEME_KEY: Preferences.Key<Int> = intPreferencesKey("theme")
internal val PREFERENCE_DYNAMIC_COLORS_KEY: Preferences.Key<Boolean> = booleanPreferencesKey("dynamic_colors")
internal val PREFERENCE_RTL_ENABLED_KEY: Preferences.Key<Boolean> = booleanPreferencesKey("rtl_enabled")
internal val PREFERENCE_NETWORK_REQUEST_DELAY_KEY: Preferences.Key<Int> = intPreferencesKey("network_request_delay")
internal val PREFERENCE_SESSION_ID_KEY: Preferences.Key<String> = stringPreferencesKey("session_id")
internal val PREFERENCE_ACCOUNT_ID_KEY: Preferences.Key<Int> = intPreferencesKey("account_id")
internal val PREFERENCE_ACCOUNT_EXPIRE_TIME_KEY: Preferences.Key<Long> = longPreferencesKey("account_expire_time")