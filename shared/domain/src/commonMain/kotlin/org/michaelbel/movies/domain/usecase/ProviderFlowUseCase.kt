package org.michaelbel.movies.domain.usecase

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.michaelbel.movies.common.dispatchers.SharedDispatchers
import org.michaelbel.movies.persistence.datastore.MoviesPreferences

class ProviderFlowUseCase(
    private val preferences: MoviesPreferences,
    dispatchers: SharedDispatchers
): FlowUseCase<Unit, String>(dispatchers.io) {

    override fun execute(params: Unit): Flow<String> {
        return preferences.getValueFlow(MoviesPreferences.PreferenceKey.PreferenceProviderKey)
            .map { key -> key ?: DEFAULT_KEY }
    }

    private companion object {
        private const val DEFAULT_KEY = "tmdb"
    }
}
