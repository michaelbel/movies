package org.michaelbel.movies.network.retrofit

import org.michaelbel.movies.network.model.DeletedSession
import org.michaelbel.movies.network.model.RequestToken
import org.michaelbel.movies.network.model.Session
import org.michaelbel.movies.network.model.SessionRequest
import org.michaelbel.movies.network.model.Token
import org.michaelbel.movies.network.model.Username
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.HTTP
import retrofit2.http.POST

@Deprecated("Use KtorAuthenticationService instead", ReplaceWith("KtorAuthenticationService"))
internal interface RetrofitAuthenticationService {

    @GET("authentication/token/new?")
    suspend fun createRequestToken(): Token

    @POST("authentication/token/validate_with_login?")
    suspend fun createSessionWithLogin(
        @Body username: Username
    ): Token

    @POST("authentication/session/new?")
    suspend fun createSession(
        @Body authToken: RequestToken
    ): Session

    @HTTP(method = "DELETE", path = "authentication/session?", hasBody = true)
    suspend fun deleteSession(
        @Body sessionRequest: SessionRequest
    ): DeletedSession
}