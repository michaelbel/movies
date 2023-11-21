package org.michaelbel.movies.interactor.usecase

import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.michaelbel.movies.common.dispatchers.MoviesDispatchers
import org.michaelbel.movies.common.usecase.UseCase
import org.michaelbel.movies.persistence.datastore.MoviesPreferences

@Deprecated("")
class DelayUseCase @Inject constructor(
    dispatchers: MoviesDispatchers,
    private val preferences: MoviesPreferences
): UseCase<Int, Unit>(dispatchers.io) {

    val networkRequestDelay: Flow<Int> = preferences.networkRequestDelayFlow.map { networkRequestDelay ->
        networkRequestDelay ?: 0
    }

    suspend fun networkRequestDelay(): Long {
        return preferences.networkRequestDelay() ?: 0L
    }

    override suspend fun execute(parameters: Int) {
        preferences.setNetworkRequestDelay(parameters)
    }
}