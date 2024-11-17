package org.michaelbel.movies.platform.impl.messaging.service

import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import org.koin.android.ext.android.inject
import org.michaelbel.movies.notifications.NotificationClient
import org.michaelbel.movies.platform.impl.messaging.ktx.mapToMoviesPush

/**
 * See [Receive messages in an Android app](https://firebase.google.com/docs/cloud-messaging/android/receive)
 */
internal class MoviesMessagingService: FirebaseMessagingService() {

    private val notificationClient: NotificationClient by inject()

    override fun onNewToken(token: String) {
        super.onNewToken(token)
    }

    override fun onMessageReceived(message: RemoteMessage) {
        notificationClient.send(message.mapToMoviesPush)
    }
}