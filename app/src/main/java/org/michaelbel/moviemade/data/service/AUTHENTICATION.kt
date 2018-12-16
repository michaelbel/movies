package org.michaelbel.moviemade.data.service

import io.reactivex.Observable
import org.michaelbel.moviemade.data.entity.*
import retrofit2.http.*

interface AUTHENTICATION {

    @GET("authentication/guest_session/new?")
    fun createGuestSession(
        @Query("api_key") apiKey: String
    ): Observable<GuestSession>

    @GET("authentication/token/new?")
    fun createRequestToken(
        @Query("api_key") apiKey: String
    ): Observable<Token>

    @POST("authentication/token/validate_with_login?")
    fun createSessionWithLogin(
        @Query("api_key") apiKey: String,
        @Body username: Username
    ): Observable<Token>

    @POST("authentication/session/new?")
    fun createSession(
        @Query("api_key") apiKey: String,
        @Body authToken: RequestToken
    ): Observable<Session>

    // createSession (from api v4)

    @HTTP(method = "DELETE", path = "authentication/session?", hasBody = true)
    fun deleteSession(
        @Query("api_key") apiKey: String,
        @Body sessionId: SessionId
    ): Observable<DeletedSession>
}