package org.michaelbel.movies.platform.messaging

interface TokenListener {

    fun onNewToken(token: String)
}