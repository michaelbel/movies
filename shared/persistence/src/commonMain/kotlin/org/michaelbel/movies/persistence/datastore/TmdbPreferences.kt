package org.michaelbel.movies.persistence.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class TmdbPreferences(
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
        data object PreferenceMovieListKey: PreferenceKey<String>(stringPreferencesKey("movie_list"))
    }
}
