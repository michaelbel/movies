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
    val theme: Flow<Int?>
        get() = dataStore.data.map { preferences -> preferences[PREFERENCE_THEME_KEY] }

    suspend fun setTheme(theme: Int) {
        dataStore.edit { preferences ->
            preferences[PREFERENCE_THEME_KEY] = theme
        }
    }

    val isDynamicColors: Flow<Boolean?>
        get() = dataStore.data.map { preferences -> preferences[PREFERENCE_DYNAMIC_COLORS_KEY] }

    suspend fun setDynamicColors(isDynamicColors: Boolean) {
        dataStore.edit { preferences ->
            preferences[PREFERENCE_DYNAMIC_COLORS_KEY] = isDynamicColors
        }
    }

    val isRtlEnabled: Flow<Boolean?>
        get() = dataStore.data.map { preferences -> preferences[PREFERENCE_RTL_ENABLED_KEY] }

    suspend fun setRtlEnabled(isRtlEnabled: Boolean) {
        dataStore.edit { preferences ->
            preferences[PREFERENCE_RTL_ENABLED_KEY] = isRtlEnabled
        }
    }

    val networkRequestDelay: Flow<Int?>
        get() = dataStore.data.map { preferences -> preferences[PREFERENCE_NETWORK_REQUEST_DELAY_KEY] }

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

    val accountId: Flow<Int?>
        get() = dataStore.data.map { preferences -> preferences[PREFERENCE_ACCOUNT_ID_KEY] }

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

    private companion object {
        private val PREFERENCE_THEME_KEY: Preferences.Key<Int> = intPreferencesKey("theme")
        private val PREFERENCE_DYNAMIC_COLORS_KEY: Preferences.Key<Boolean> = booleanPreferencesKey("dynamic_colors")
        private val PREFERENCE_RTL_ENABLED_KEY: Preferences.Key<Boolean> = booleanPreferencesKey("rtl_enabled")
        private val PREFERENCE_NETWORK_REQUEST_DELAY_KEY: Preferences.Key<Int> = intPreferencesKey("network_request_delay")
        private val PREFERENCE_SESSION_ID_KEY: Preferences.Key<String> = stringPreferencesKey("session_id")
        private val PREFERENCE_ACCOUNT_ID_KEY: Preferences.Key<Int> = intPreferencesKey("account_id")
        private val PREFERENCE_ACCOUNT_EXPIRE_TIME_KEY: Preferences.Key<Long> = longPreferencesKey("account_expire_time")
    }
}