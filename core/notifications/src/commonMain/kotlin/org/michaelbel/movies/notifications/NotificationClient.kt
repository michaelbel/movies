package org.michaelbel.movies.notifications

import org.michaelbel.movies.notifications.model.MoviesPush

interface NotificationClient {

    suspend fun notificationsPermissionRequired(time: Long): Boolean

    suspend fun updateNotificationExpireTime()

    fun send(push: MoviesPush)

    fun sendDownloadImageNotification(
        notificationId: Int,
        contentTitleRes: Int,
        contentTextRes: Int
    )

    fun cancelDownloadImageNotification(notificationId: Int)
}