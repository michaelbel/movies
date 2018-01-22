package org.michaelbel.moviemade.rest.api.service;

import org.michaelbel.moviemade.rest.model.v3.Review;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface REVIEWS {

    @GET("review/{review_id}")
    Observable<Review> getDetails(
        @Path("review_id") String id,
        @Query("api_key") String apiKey
    );
}