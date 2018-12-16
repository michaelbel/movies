package org.michaelbel.moviemade.data.service

import io.reactivex.Observable
import org.michaelbel.moviemade.data.entity.Network
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface NETWORKS {

    @GET("network/{network_id}?")
    fun getDetails(
        @Path("network_id") id: Int,
        @Query("api_key") apiKey: String
    ): Observable<Network>

    // getAlternativeNames

    // getImages
}