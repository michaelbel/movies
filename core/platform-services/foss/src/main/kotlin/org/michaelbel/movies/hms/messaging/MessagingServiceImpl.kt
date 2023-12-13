package org.michaelbel.movies.hms.messaging

import javax.inject.Inject
import org.michaelbel.movies.platform.main.messaging.MessagingService
import org.michaelbel.movies.platform.main.messaging.TokenListener

internal class MessagingServiceImpl @Inject constructor(): MessagingService {

    override fun setTokenListener(listener: TokenListener) {}
}