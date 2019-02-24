package org.michaelbel.moviemade.core.remote

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CreditsService {

    @GET("credit/{credit_id}?")
    fun getDetails(
        @Path("credit_id") id: String,
        @Query("api_key") apiKey: String
    ): Observable<*>
}