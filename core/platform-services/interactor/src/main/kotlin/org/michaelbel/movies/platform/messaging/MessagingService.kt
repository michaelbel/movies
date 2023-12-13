package org.michaelbel.movies.platform.messaging

interface MessagingService {

    fun setTokenListener(listener: TokenListener)
}