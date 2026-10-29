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

    fun <T> getValueFlow(key: PreferenceKey<T>): Flow<T?> {
        return dataStore.data.map { preferences -> preferences[key.preferenceKey] }
    }

    suspend fun <T> getValue(key: PreferenceKey<T>): T? {
        return dataStore.data.first()[key.preferenceKey]
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

    suspend fun removeValues(vararg keys: PreferenceKey<*>) {
        dataStore.edit { preferences ->
            keys.forEach { key ->
                preferences.remove(key.preferenceKey)
            }
        }
    }

    sealed class PreferenceKey<T>(
        val preferenceKey: Preferences.Key<T>
    ) {
        data object PreferenceThemeKey: PreferenceKey<String>(stringPreferencesKey("theme"))
        data object PreferenceFeedViewKey: PreferenceKey<String>(stringPreferencesKey("feed_view"))
        data object PreferenceMovieListKey: PreferenceKey<String>(stringPreferencesKey("movie_list"))
        data object PreferenceDynamicColorsKey: PreferenceKey<Boolean>(booleanPreferencesKey("dynamic_colors"))
        data object PreferencePaletteColorsKey: PreferenceKey<Boolean>(booleanPreferencesKey("palette_colors"))
        data object PreferenceSessionIdKey: PreferenceKey<String>(stringPreferencesKey("session_id"))
        data object PreferenceAccountKey: PreferenceKey<Int>(intPreferencesKey("account_id"))
        data object PreferenceAccountExpireTimeKey: PreferenceKey<Long>(longPreferencesKey("account_expire_time"))
        data object PreferenceNotificationExpireTimeKey: PreferenceKey<Long>(longPreferencesKey("notification_expire_time"))
        data object PreferenceBiometricKey: PreferenceKey<Boolean>(booleanPreferencesKey("biometric"))
        data object PreferenceProviderKey: PreferenceKey<String>(stringPreferencesKey("provider"))
        data object PreferencePaletteKey: PreferenceKey<Int>(intPreferencesKey("palette"))
        data object PreferenceSeedColorKey: PreferenceKey<Int>(intPreferencesKey("seed_color"))
        data object PreferenceScreenshotBlockKey: PreferenceKey<Boolean>(booleanPreferencesKey("screenshot_block"))
    }
}
