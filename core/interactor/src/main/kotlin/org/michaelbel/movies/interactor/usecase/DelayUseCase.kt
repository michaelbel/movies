package org.michaelbel.movies.interactor.usecase

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.michaelbel.movies.common.dispatchers.MoviesDispatchers
import org.michaelbel.movies.common.usecase.UseCase
import org.michaelbel.movies.persistence.datastore.MoviesPreferences
import javax.inject.Inject

class DelayUseCase @Inject constructor(
    dispatchers: MoviesDispatchers,
    private val preferences: MoviesPreferences
): UseCase<Int, Unit>(dispatchers.io) {

    val networkRequestDelay: Flow<Int> = preferences.networkRequestDelay.map { networkRequestDelay ->
        networkRequestDelay ?: 0
    }

    suspend fun networkRequestDelay(): Long {
        return preferences.getNetworkRequestDelay() ?: 0L
    }

    override suspend fun execute(parameters: Int) {
        preferences.setNetworkRequestDelay(parameters)
    }
}