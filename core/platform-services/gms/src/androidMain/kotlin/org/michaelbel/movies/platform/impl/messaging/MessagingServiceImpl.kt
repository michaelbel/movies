package org.michaelbel.movies.platform.impl.messaging

import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.tasks.await
import org.michaelbel.movies.platform.messaging.MessagingService
import org.michaelbel.movies.platform.messaging.TokenListener

class MessagingServiceImpl(
    private val firebaseMessaging: FirebaseMessaging
): MessagingService {

    override fun setTokenListener(listener: TokenListener) {
        firebaseMessaging.token.addOnCompleteListener { task ->
            val token = task.result
            listener.onNewToken(token)
        }
    }

    override suspend fun awaitToken(): String {
        return firebaseMessaging.token.await()
    }
}