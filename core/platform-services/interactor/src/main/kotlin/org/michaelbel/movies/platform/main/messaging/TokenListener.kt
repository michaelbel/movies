package org.michaelbel.movies.platform.main.messaging

interface TokenListener {
    fun onNewToken(token: String)
}