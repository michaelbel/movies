package org.michaelbel.moviemade.data.service

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface AUTHENTICATION {

    @GET("authentication/guest_session/new?")
    fun createGuestSession(
        @Query("api_key") apiKey: String
    ): Observable<*>

    @GET("authentication/token/new?")
    fun createRequestToken(
        @Query("api_key") apiKey: String
    ): Observable<*>

    @GET("authentication/session/new?")
    fun createSession(
        @Query("api_key") apiKey: String,
        @Query("request_token") requestToken: String
    ): Observable<*>

    // createSessionWithLogin

    // createSession (from v4 access)

    // deleteSession
}