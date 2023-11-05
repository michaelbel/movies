package org.michaelbel.movies.notifications

import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import dagger.hilt.android.AndroidEntryPoint
import org.michaelbel.movies.notifications.ktx.mapToMoviesPush
import javax.inject.Inject

/**
 * See [Receive messages in an Android app](https://firebase.google.com/docs/cloud-messaging/android/receive)
 */
@AndroidEntryPoint
internal class MoviesMessagingService: FirebaseMessagingService() {

    @Inject lateinit var notificationClient: NotificationClient

    override fun onMessageReceived(message: RemoteMessage) {
        notificationClient.send(message.mapToMoviesPush)
    }
}