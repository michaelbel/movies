package org.michaelbel.movies.domain.service.account

import org.michaelbel.movies.network.model.Account
import retrofit2.http.GET
import retrofit2.http.Query

internal interface AccountService {

    @GET("account")
    suspend fun accountDetails(
        @Query("api_key") apiKey: String,
        @Query("session_id") sessionId: String
    ): Account
}