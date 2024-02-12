package org.michaelbel.movies.repository.impl

import javax.inject.Inject
import javax.inject.Singleton
import org.michaelbel.movies.persistence.datastore.MoviesPreferences
import org.michaelbel.movies.repository.NotificationRepository

@Singleton
internal class NotificationRepositoryImpl @Inject constructor(
    private val preferences: MoviesPreferences
): NotificationRepository {

    override suspend fun notificationExpireTime(): Long {
        return preferences.notificationExpireTime() ?: 0L
    }

    override suspend fun updateNotificationExpireTime() {
        val currentTime = System.currentTimeMillis()
        preferences.setNotificationExpireTime(currentTime)
    }
}