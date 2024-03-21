@file:Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")

package org.michaelbel.movies.network

import org.michaelbel.movies.network.ktor.KtorAccountService
import org.michaelbel.movies.network.model.Account
import org.michaelbel.movies.network.retrofit.RetrofitAccountService
import javax.inject.Inject

/**
 * You can replace [ktorAccountService] with [retrofitAccountService] to use it.
 */
actual class AccountNetworkService @Inject internal constructor(
    private val retrofitAccountService: RetrofitAccountService,
    private val ktorAccountService: KtorAccountService
) {

    suspend fun accountDetails(
        sessionId: String
    ): Account {
        return ktorAccountService.accountDetails(sessionId)
    }
}