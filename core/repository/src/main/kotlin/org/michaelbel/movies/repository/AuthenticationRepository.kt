package org.michaelbel.movies.repository

import org.michaelbel.movies.network.model.Session
import org.michaelbel.movies.network.model.Token

interface AuthenticationRepository {

    suspend fun createRequestToken(loginViaTmdb: Boolean): Token

    suspend fun createSessionWithLogin(
        username: String,
        password: String,
        requestToken: String
    ): Token

    suspend fun createSession(token: String): Session

    suspend fun deleteSession()
}