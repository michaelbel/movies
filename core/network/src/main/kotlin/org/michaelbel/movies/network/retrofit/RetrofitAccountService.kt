package org.michaelbel.movies.network.retrofit

import org.michaelbel.movies.network.model.Account
import retrofit2.http.GET
import retrofit2.http.Query

@Deprecated("Use KtorAccountService instead", ReplaceWith("KtorAccountService"))
internal interface RetrofitAccountService {

    @GET("account")
    suspend fun accountDetails(
        @Query("session_id") sessionId: String
    ): Account
}