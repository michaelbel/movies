package org.michaelbel.movies.repository.impl

import org.michaelbel.movies.persistence.database.ktx.orEmpty
import org.michaelbel.movies.persistence.datastore.MoviesPreferences
import org.michaelbel.movies.repository.NotificationRepository

internal class NotificationRepositoryImpl(
    private val preferences: MoviesPreferences
): NotificationRepository {

    override suspend fun notificationExpireTime(): Long {
        return preferences.notificationExpireTime().orEmpty()
    }

    override suspend fun updateNotificationExpireTime() {
        val currentTime = System.currentTimeMillis()
        preferences.setValue(MoviesPreferences.PreferenceKey.PreferenceNotificationExpireTimeKey, currentTime)
    }
}