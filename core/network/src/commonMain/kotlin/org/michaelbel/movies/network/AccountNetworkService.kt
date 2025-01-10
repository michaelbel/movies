package org.michaelbel.movies.network

import org.michaelbel.movies.network.ktor.KtorAccountService
import org.michaelbel.movies.network.model.Account

class AccountNetworkService internal constructor(
    private val ktorAccountService: KtorAccountService
) {

    suspend fun accountDetails(
        sessionId: String
    ): Account {
        return ktorAccountService.accountDetails(sessionId)
    }
}