package org.michaelbel.movies.platform.gms.messaging

import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import org.michaelbel.movies.notifications.NotificationClient

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