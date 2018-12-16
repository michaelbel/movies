package org.michaelbel.moviemade.data.service

import io.reactivex.Observable
import org.michaelbel.moviemade.data.entity.Review
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface REVIEWS {

    @GET("review/{review_id}?")
    fun getDetails(
        @Path("review_id") id: String,
        @Query("api_key") apiKey: String
    ): Observable<Review>
}