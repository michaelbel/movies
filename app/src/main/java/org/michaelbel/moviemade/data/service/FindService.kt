package org.michaelbel.moviemade.data.service

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface FindService {

    @GET("find/{external_id}?")
    fun findById(
        @Path("external_id") id: String,
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("external_source") ex_source: String
    ): Observable<*>
}