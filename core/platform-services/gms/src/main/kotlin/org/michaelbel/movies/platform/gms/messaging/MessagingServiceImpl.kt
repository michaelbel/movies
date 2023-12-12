package org.michaelbel.movies.platform.gms.messaging

import com.google.firebase.messaging.FirebaseMessaging
import javax.inject.Inject
import org.michaelbel.movies.platform.main.messaging.MessagingService
import org.michaelbel.movies.platform.main.messaging.TokenListener

internal class MessagingServiceImpl @Inject constructor(
    private val firebaseMessaging: FirebaseMessaging
): MessagingService {

    override fun setTokenListener(listener: TokenListener) {
        firebaseMessaging.token.addOnCompleteListener { task ->
            val token: String = task.result
            listener.onNewToken(token)
        }
    }
}