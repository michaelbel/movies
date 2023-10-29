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
import javax.inject.Inject

class MoviesPreferences @Inject constructor(
    private val dataStore: DataStore<Preferences>
) {

    val themeFlow: Flow<Int?>
        get() = dataStore.data.map { preferences -> preferences[PREFERENCE_THEME_KEY] }

    val feedViewFlow: Flow<String?>
        get() = dataStore.data.map { preferences -> preferences[PREFERENCE_FEED_VIEW_KEY] }

    val movieListFlow: Flow<String?>
        get() = dataStore.data.map { preferences -> preferences[PREFERENCE_MOVIE_LIST_KEY] }

    val isDynamicColorsFlow: Flow<Boolean?>
        get() = dataStore.data.map { preferences -> preferences[PREFERENCE_DYNAMIC_COLORS_KEY] }

    val isRtlEnabledFlow: Flow<Boolean?>
        get() = dataStore.data.map { preferences -> preferences[PREFERENCE_RTL_ENABLED_KEY] }

    val networkRequestDelayFlow: Flow<Int?>
        get() = dataStore.data.map { preferences -> preferences[PREFERENCE_NETWORK_REQUEST_DELAY_KEY] }

    val accountIdFlow: Flow<Int?>
        get() = dataStore.data.map { preferences -> preferences[PREFERENCE_ACCOUNT_ID_KEY] }

    suspend fun setTheme(theme: Int) {
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

    suspend fun setRtlEnabled(isRtlEnabled: Boolean) {
        dataStore.edit { preferences ->
            preferences[PREFERENCE_RTL_ENABLED_KEY] = isRtlEnabled
        }
    }

    suspend fun getNetworkRequestDelay(): Long? {
        return dataStore.data.first()[PREFERENCE_NETWORK_REQUEST_DELAY_KEY]?.toLong()
    }

    suspend fun setNetworkRequestDelay(delay: Int) {
        dataStore.edit { preferences ->
            preferences[PREFERENCE_NETWORK_REQUEST_DELAY_KEY] = delay
        }
    }

    suspend fun getSessionId(): String? {
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

    suspend fun getAccountId(): Int? {
        return dataStore.data.first()[PREFERENCE_ACCOUNT_ID_KEY]
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

    suspend fun getAccountExpireTime(): Long? {
        return dataStore.data.first()[PREFERENCE_ACCOUNT_EXPIRE_TIME_KEY]
    }

    suspend fun setAccountExpireTime(expireTime: Long) {
        dataStore.edit { preferences ->
            preferences[PREFERENCE_ACCOUNT_EXPIRE_TIME_KEY] = expireTime
        }
    }

    suspend fun getNotificationExpireTime(): Long? {
        return dataStore.data.first()[PREFERENCE_NOTIFICATION_EXPIRE_TIME_KEY]
    }

    suspend fun setNotificationExpireTime(expireTime: Long) {
        dataStore.edit { preferences ->
            preferences[PREFERENCE_NOTIFICATION_EXPIRE_TIME_KEY] = expireTime
        }
    }

    private companion object {
        private val PREFERENCE_THEME_KEY: Preferences.Key<Int> = intPreferencesKey("theme")
        private val PREFERENCE_FEED_VIEW_KEY: Preferences.Key<String> = stringPreferencesKey("feed_view")
        private val PREFERENCE_MOVIE_LIST_KEY: Preferences.Key<String> = stringPreferencesKey("movie_list")
        private val PREFERENCE_DYNAMIC_COLORS_KEY: Preferences.Key<Boolean> = booleanPreferencesKey("dynamic_colors")
        private val PREFERENCE_RTL_ENABLED_KEY: Preferences.Key<Boolean> = booleanPreferencesKey("rtl_enabled")
        private val PREFERENCE_NETWORK_REQUEST_DELAY_KEY: Preferences.Key<Int> = intPreferencesKey("network_request_delay")
        private val PREFERENCE_SESSION_ID_KEY: Preferences.Key<String> = stringPreferencesKey("session_id")
        private val PREFERENCE_ACCOUNT_ID_KEY: Preferences.Key<Int> = intPreferencesKey("account_id")
        private val PREFERENCE_ACCOUNT_EXPIRE_TIME_KEY: Preferences.Key<Long> = longPreferencesKey("account_expire_time")
        private val PREFERENCE_NOTIFICATION_EXPIRE_TIME_KEY: Preferences.Key<Long> = longPreferencesKey("notification_expire_time")
    }
}