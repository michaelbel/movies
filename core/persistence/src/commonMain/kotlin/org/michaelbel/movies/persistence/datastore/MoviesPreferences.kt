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
import org.michaelbel.movies.persistence.database.ktx.orEmpty

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
        return dataStore.data.first()[PREFERENCE_BIOMETRIC_KEY].orEmpty()
    }

    suspend fun accountId(): Int {
        return dataStore.data.first()[PREFERENCE_ACCOUNT_ID_KEY].orEmpty()
    }

    suspend fun accountExpireTime(): Long? {
        return dataStore.data.first()[PREFERENCE_ACCOUNT_EXPIRE_TIME_KEY]
    }

    suspend fun notificationExpireTime(): Long? {
        return dataStore.data.first()[PREFERENCE_NOTIFICATION_EXPIRE_TIME_KEY]
    }

    suspend fun sessionId(): String? {
        return dataStore.data.first()[PREFERENCE_SESSION_ID_KEY]
    }

    suspend fun <T> setValue(key: PreferenceKey<T>, value: T) {
        dataStore.edit { preferences ->
            preferences[key.preferenceKey] = value
        }
    }

    suspend fun <T> removeValue(key: PreferenceKey<T>) {
        dataStore.edit { preferences ->
            preferences.remove(key.preferenceKey)
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

    sealed class PreferenceKey<T>(
        val preferenceKey: Preferences.Key<T>
    ) {
        data object PreferenceThemeKey: PreferenceKey<String>(PREFERENCE_THEME_KEY)
        data object PreferenceFeedViewKey: PreferenceKey<String>(PREFERENCE_FEED_VIEW_KEY)
        data object PreferenceMovieListKey: PreferenceKey<String>(PREFERENCE_MOVIE_LIST_KEY)
        data object PreferenceDynamicColorsKey: PreferenceKey<Boolean>(PREFERENCE_DYNAMIC_COLORS_KEY)
        data object PreferenceSessionIdKey: PreferenceKey<String>(PREFERENCE_SESSION_ID_KEY)
        data object PreferenceAccountKey: PreferenceKey<Int>(PREFERENCE_ACCOUNT_ID_KEY)
        data object PreferenceAccountExpireTimeKey: PreferenceKey<Long>(PREFERENCE_ACCOUNT_EXPIRE_TIME_KEY)
        data object PreferenceNotificationExpireTimeKey: PreferenceKey<Long>(PREFERENCE_NOTIFICATION_EXPIRE_TIME_KEY)
        data object PreferenceBiometricKey: PreferenceKey<Boolean>(PREFERENCE_BIOMETRIC_KEY)
        data object PreferencePaletteKey: PreferenceKey<Int>(PREFERENCE_PALETTE_KEY)
        data object PreferenceSeedColorKey: PreferenceKey<Int>(PREFERENCE_SEED_COLOR_KEY)
        data object PreferenceScreenshotBlockKey: PreferenceKey<Boolean>(PREFERENCE_SCREENSHOT_BLOCK_KEY)
    }
}