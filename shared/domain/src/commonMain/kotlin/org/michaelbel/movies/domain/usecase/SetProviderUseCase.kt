@file:Suppress("PARAMETER_NAME_CHANGED_ON_OVERRIDE")

package org.michaelbel.movies.domain.usecase

import org.michaelbel.movies.common.dispatchers.SharedDispatchers
import org.michaelbel.movies.persistence.datastore.MoviesPreferences

class SetProviderUseCase(
    private val preferences: MoviesPreferences,
    dispatchers: SharedDispatchers
): UseCase<String, Unit>(dispatchers.io) {

    override suspend fun execute(providerKey: String) {
        preferences.setValue(MoviesPreferences.PreferenceKey.PreferenceProviderKey, providerKey)
    }
}
