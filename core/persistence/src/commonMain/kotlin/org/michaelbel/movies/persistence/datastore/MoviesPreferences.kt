package org.michaelbel.movies.persistence.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class MoviesPreferences(
    private val dataStore: DataStore<Preferences>
) {

    val themeFlow: Flow<String?>
        get() = dataStore.data.map { preferences -> preferences[PREFERENCE_THEME_KEY] }

    val feedViewFlow: Flow<String?>
        get() = dataStore.data.map { preferences -> preferences[PREFERENCE_FEED_VIEW_KEY] }

    val movieListFlow: Flow<String?>
        get() = dataStore.data.map { preferences -> preferences[PREFERENCE_MOVIE_LIST_KEY] }

    val isDynamicColorsFlow: Flow<Boolean?>
        get() = dataStore.data.map { preferences -> preferences[PREFERENCE_DYNAMIC_COLORS_KEY] }

    val isBiometricEnabledFlow: Flow<Boolean?>
        get() = dataStore.data.map { preferences -> preferences[PREFERENCE_BIOMETRIC_KEY] }

    val isScreenshotBlockEnabledFlow: Flow<Boolean?>
        get() = dataStore.data.map { preferences -> preferences[PREFERENCE_SCREENSHOT_BLOCK_KEY] }

    val paletteKeyFlow: Flow<Int?>
        get() = dataStore.data.map { preferences -> preferences[PREFERENCE_PALETTE_KEY] }

    val seedColorFlow: Flow<Int?>
        get() = dataStore.data.map { preferences -> preferences[PREFERENCE_SEED_COLOR_KEY] }

    val accountIdFlow: Flow<Int?>
        get() = dataStore.data.map { preferences -> preferences[PREFERENCE_ACCOUNT_ID_KEY] }

    suspend fun isBiometricEnabledAsync(): Boolean {
        return dataStore.data.first()[PREFERENCE_BIOMETRIC_KEY] ?: false
    }

    suspend fun setTheme(theme: String) {
        dataStore.edit { preferences ->
            preferences[PREFERENCE_THEME_KEY] = theme
        }
    }

    suspend fun setFeedView(feedView: String) {
        dataStore.edit { preferences ->
            preferences[PREFERENCE_FEED_VIEW_KEY] = feedView
        }
    }

    suspend fun setMovieList(movieList: String) {
        dataStore.edit { preferences ->
            preferences[PREFERENCE_MOVIE_LIST_KEY] = movieList
        }
    }

    suspend fun setDynamicColors(isDynamicColors: Boolean) {
        dataStore.edit { preferences ->
            preferences[PREFERENCE_DYNAMIC_COLORS_KEY] = isDynamicColors
        }
    }

    suspend fun sessionId(): String? {
        return dataStore.data.first()[PREFERENCE_SESSION_ID_KEY]
    }

    suspend fun setSessionId(sessionId: String) {
        dataStore.edit { preferences ->
            preferences[PREFERENCE_SESSION_ID_KEY] = sessionId
        }
    }

    suspend fun removeSessionId() {
        dataStore.edit { preferences ->
            preferences.remove(PREFERENCE_SESSION_ID_KEY)
        }
    }

    suspend fun accountId(): Int {
        return dataStore.data.first()[PREFERENCE_ACCOUNT_ID_KEY] ?: 0
    }

    suspend fun setAccountId(accountId: Int) {
        dataStore.edit { preferences ->
            preferences[PREFERENCE_ACCOUNT_ID_KEY] = accountId
        }
    }

    suspend fun removeAccountId() {
        dataStore.edit { preferences ->
            preferences.remove(PREFERENCE_ACCOUNT_ID_KEY)
        }
    }

    suspend fun accountExpireTime(): Long? {
        return dataStore.data.first()[PREFERENCE_ACCOUNT_EXPIRE_TIME_KEY]
    }

    suspend fun setAccountExpireTime(expireTime: Long) {
        dataStore.edit { preferences ->
            preferences[PREFERENCE_ACCOUNT_EXPIRE_TIME_KEY] = expireTime
        }
    }

    suspend fun notificationExpireTime(): Long? {
        return dataStore.data.first()[PREFERENCE_NOTIFICATION_EXPIRE_TIME_KEY]
    }

    suspend fun setNotificationExpireTime(expireTime: Long) {
        dataStore.edit { preferences ->
            preferences[PREFERENCE_NOTIFICATION_EXPIRE_TIME_KEY] = expireTime
        }
    }

    suspend fun setBiometricEnabled(enabled: Boolean) {
        dataStore.edit { preferences ->
            preferences[PREFERENCE_BIOMETRIC_KEY] = enabled
        }
    }

    suspend fun setScreenshotBlockEnabled(enabled: Boolean) {
        dataStore.edit { preferences ->
            preferences[PREFERENCE_SCREENSHOT_BLOCK_KEY] = enabled
        }
    }

    suspend fun setPaletteKey(paletteKey: Int) {
        dataStore.edit { preferences ->
            preferences[PREFERENCE_PALETTE_KEY] = paletteKey
        }
    }

    suspend fun setSeedColor(seedColor: Int) {
        dataStore.edit { preferences ->
            preferences[PREFERENCE_SEED_COLOR_KEY] = seedColor
        }
    }

    private companion object {
        private val PREFERENCE_THEME_KEY = stringPreferencesKey("theme")
        private val PREFERENCE_FEED_VIEW_KEY = stringPreferencesKey("feed_view")
        private val PREFERENCE_MOVIE_LIST_KEY = stringPreferencesKey("movie_list")
        private val PREFERENCE_DYNAMIC_COLORS_KEY = booleanPreferencesKey("dynamic_colors")
        private val PREFERENCE_SESSION_ID_KEY = stringPreferencesKey("session_id")
        private val PREFERENCE_ACCOUNT_ID_KEY = intPreferencesKey("account_id")
        private val PREFERENCE_ACCOUNT_EXPIRE_TIME_KEY = longPreferencesKey("account_expire_time")
        private val PREFERENCE_NOTIFICATION_EXPIRE_TIME_KEY = longPreferencesKey("notification_expire_time")
        private val PREFERENCE_BIOMETRIC_KEY = booleanPreferencesKey("biometric")
        private val PREFERENCE_PALETTE_KEY = intPreferencesKey("palette")
        private val PREFERENCE_SEED_COLOR_KEY = intPreferencesKey("seed_color")
        private val PREFERENCE_SCREENSHOT_BLOCK_KEY = booleanPreferencesKey("screenshot_block")
    }
}