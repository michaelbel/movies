package org.michaelbel.movies.platform.impl.messaging

import javax.inject.Inject
import org.michaelbel.movies.platform.messaging.MessagingService
import org.michaelbel.movies.platform.messaging.TokenListener

class MessagingServiceImpl @Inject constructor(): MessagingService {

    override fun setTokenListener(listener: TokenListener) {}
}