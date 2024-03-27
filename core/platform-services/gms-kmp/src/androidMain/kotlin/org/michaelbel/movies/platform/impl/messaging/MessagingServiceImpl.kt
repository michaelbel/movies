package org.michaelbel.movies.platform.impl.messaging

import com.google.firebase.messaging.FirebaseMessaging
import org.michaelbel.movies.platform.messaging.MessagingService
import org.michaelbel.movies.platform.messaging.TokenListener

class MessagingServiceImpl(
    private val firebaseMessaging: FirebaseMessaging
): MessagingService {

    override fun setTokenListener(listener: TokenListener) {
        firebaseMessaging.token.addOnCompleteListener { task ->
            val token: String = task.result
            listener.onNewToken(token)
        }
    }
}