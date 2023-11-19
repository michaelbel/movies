package org.michaelbel.movies.platform.main.messaging

interface MessagingService {

    fun setTokenListener(listener: TokenListener)
}