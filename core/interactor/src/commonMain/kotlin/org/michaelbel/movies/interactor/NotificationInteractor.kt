package org.michaelbel.movies.interactor

interface NotificationInteractor {

    suspend fun notificationExpireTime(): Long

    suspend fun updateNotificationExpireTime()
}