package org.michaelbel.movies.network.service.tmdb.account

import org.michaelbel.movies.network.model.Account
import retrofit2.http.GET
import retrofit2.http.Query

interface TmdbAccountService {

    @GET("account")
    suspend fun accountDetails(
        @Query("session_id") sessionId: String
    ): Account
}