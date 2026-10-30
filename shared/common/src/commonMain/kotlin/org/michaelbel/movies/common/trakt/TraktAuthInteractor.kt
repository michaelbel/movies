package org.michaelbel.movies.common.trakt

interface TraktAuthInteractor {

    val isSupported: Boolean

    suspend fun authorize(clientId: String, redirectUri: String): TraktAuthResult
}
