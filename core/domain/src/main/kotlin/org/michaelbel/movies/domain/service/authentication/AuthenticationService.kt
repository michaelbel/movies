package org.michaelbel.movies.domain.service.authentication

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
import retrofit2.http.Query

internal interface AuthenticationService {

    @GET("authentication/token/new?")
    suspend fun createRequestToken(
        @Query("api_key") apiKey: String
    ): Token

    @POST("authentication/token/validate_with_login?")
    suspend fun createSessionWithLogin(
        @Query("api_key") apiKey: String,
        @Body username: Username
    ): Token

    @POST("authentication/session/new?")
    suspend fun createSession(
        @Query("api_key") apiKey: String,
        @Body authToken: RequestToken
    ): Session

    @HTTP(method = "DELETE", path = "authentication/session?", hasBody = true)
    suspend fun deleteSession(
        @Query("api_key") apiKey: String,
        @Body sessionRequest: SessionRequest
    ): DeletedSession
}