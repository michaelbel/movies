package org.michaelbel.movies.notifications.impl

import org.michaelbel.movies.interactor.Interactor
import org.michaelbel.movies.notifications.NotificationClient
import org.michaelbel.movies.notifications.model.MoviesPush

class NotificationClientImpl(
    private val interactor: Interactor
): NotificationClient {

    override suspend fun notificationsPermissionRequired(time: Long): Boolean {
        return false
    }

    override suspend fun updateNotificationExpireTime() {
        interactor.updateNotificationExpireTime()
    }

    override fun send(push: MoviesPush) {}

    override fun sendDownloadImageNotification(
        notificationId: Int,
        contentTitleRes: Int,
        contentTextRes: Int
    ) {}

    override fun cancelDownloadImageNotification(notificationId: Int) {}
}