package org.michaelbel.movies.platform.impl.messaging.service

import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import org.michaelbel.movies.notifications.NotificationClient
import org.michaelbel.movies.platform.impl.messaging.ktx.mapToMoviesPush

/**
 * See [Receive messages in an Android app](https://firebase.google.com/docs/cloud-messaging/android/receive)
 */
@AndroidEntryPoint
internal class MoviesMessagingService: FirebaseMessagingService() {

    @Inject
    lateinit var notificationClient: NotificationClient

    override fun onMessageReceived(message: RemoteMessage) {
        notificationClient.send(message.mapToMoviesPush)
    }
}