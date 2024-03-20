package org.michaelbel.movies.network

import org.michaelbel.movies.network.ktor.KtorAccountService
import org.michaelbel.movies.network.model.Account
import org.michaelbel.movies.network.retrofit.RetrofitAccountService
import javax.inject.Inject

/**
 * You can replace [ktorAccountService] with [retrofitAccountService] to use it.
 */
class AccountNetworkService @Inject internal constructor(
    private val retrofitAccountService: RetrofitAccountService,
    private val ktorAccountService: KtorAccountService
) {

    suspend fun accountDetails(
        sessionId: String
    ): Account {
        return ktorAccountService.accountDetails(sessionId)
    }
}