package org.michaelbel.movies.domain.usecase

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import org.michaelbel.movies.common.dispatchers.MoviesDispatchers
import org.michaelbel.movies.common.usecase.UseCase
import org.michaelbel.movies.domain.preferences.constants.PREFERENCE_NETWORK_REQUEST_DELAY_KEY
import javax.inject.Inject

class DelayUseCase @Inject constructor(
    dispatchers: MoviesDispatchers,
    private val dataStore: DataStore<Preferences>
): UseCase<Int, Unit>(dispatchers.io) {

    val networkRequestDelay: Flow<Int> = dataStore.data.map { preferences ->
        preferences[PREFERENCE_NETWORK_REQUEST_DELAY_KEY] ?: 0
    }

    suspend fun networkRequestDelay(): Long {
        return dataStore.data.first()[PREFERENCE_NETWORK_REQUEST_DELAY_KEY]?.toLong() ?: 0L
    }

    override suspend fun execute(parameters: Int) {
        dataStore.edit { preferences ->
            preferences[PREFERENCE_NETWORK_REQUEST_DELAY_KEY] = parameters
        }
    }
}