package org.michaelbel.application.rest.api;

import org.michaelbel.application.rest.model.Review;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

@SuppressWarnings("all")
public interface REVIEWS {

    @GET("review/{review_id}")
    Call<Review> getDetails(
            @Path("review_id") String id,
            @Query("api_key") String apiKey
    );
}