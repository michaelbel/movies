package org.michaelbel.movies.network.service.account

import org.michaelbel.movies.network.model.Account
import retrofit2.http.GET
import retrofit2.http.Query

interface AccountService {

    @GET("account")
    suspend fun accountDetails(
        @Query("api_key") apiKey: String,
        @Query("session_id") sessionId: String
    ): Account
}