package org.michaelbel.movies.repository

import javax.inject.Inject
import javax.inject.Singleton
import org.michaelbel.movies.persistence.datastore.MoviesPreferences

@Singleton
internal class NotificationRepositoryImpl @Inject constructor(
    private val preferences: MoviesPreferences
): NotificationRepository {

    override suspend fun notificationExpireTime(): Long {
        return preferences.getNotificationExpireTime() ?: 0L
    }

    override suspend fun updateNotificationExpireTime() {
        val currentTime: Long = System.currentTimeMillis()
        preferences.setNotificationExpireTime(currentTime)
    }
}