package org.michaelbel.moviemade.data.service

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CREDITS {

    @GET("credit/{credit_id}?")
    fun getDetails(
        @Path("credit_id") id: String,
        @Query("api_key") apiKey: String
    ): Observable<*>
}