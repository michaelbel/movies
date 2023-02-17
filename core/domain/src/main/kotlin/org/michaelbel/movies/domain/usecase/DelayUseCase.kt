package org.michaelbel.movies.domain.usecase

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import org.michaelbel.movies.common.coroutines.Dispatcher
import org.michaelbel.movies.common.coroutines.MoviesDispatchers
import org.michaelbel.movies.common.usecase.UseCase
import org.michaelbel.movies.domain.datasource.ktx.PREFERENCE_NETWORK_REQUEST_DELAY

class DelayUseCase @Inject constructor(
    @Dispatcher(MoviesDispatchers.IO) private val dispatcher: CoroutineDispatcher,
    private val dataStore: DataStore<Preferences>
): UseCase<Int, Unit>(dispatcher) {

    val networkRequestDelay: Flow<Int> = dataStore.data.map { preferences ->
        preferences[PREFERENCE_NETWORK_REQUEST_DELAY] ?: 0
    }

    suspend fun networkRequestDelay(): Long {
        return dataStore.data.first()[PREFERENCE_NETWORK_REQUEST_DELAY]?.toLong() ?: 0L
    }

    override suspend fun execute(parameters: Int) {
        dataStore.edit { preferences ->
            preferences[PREFERENCE_NETWORK_REQUEST_DELAY] = parameters
        }
    }
}