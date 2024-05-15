package org.michaelbel.movies.platform.impl.messaging

import org.michaelbel.movies.platform.messaging.MessagingService
import org.michaelbel.movies.platform.messaging.TokenListener

class MessagingServiceImpl: MessagingService {

    override fun setTokenListener(listener: TokenListener) {}

    override suspend fun awaitToken(): String {
        return ""
    }
}