package org.michaelbel.movies.domain.usecase

import org.michaelbel.movies.common.dispatchers.SharedDispatchers
import org.michaelbel.movies.persistence.datastore.MoviesPreferences
import kotlin.time.Clock

class UpdateNotificationExpireTimeUseCase(
    private val preferences: MoviesPreferences,
    dispatchers: SharedDispatchers
): UseCase<Unit, Unit>(dispatchers.io) {

    override suspend fun execute(params: Unit) {
        val currentTime = Clock.System.now().toEpochMilliseconds()
        preferences.setValue(MoviesPreferences.PreferenceKey.PreferenceNotificationExpireTimeKey, currentTime)
    }
}
