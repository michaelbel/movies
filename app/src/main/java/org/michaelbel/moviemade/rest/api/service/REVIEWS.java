package org.michaelbel.moviemade.rest.api.service;

import org.michaelbel.moviemade.rest.base.TmdbService;
import org.michaelbel.moviemade.rest.model.v3.Review;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface REVIEWS extends TmdbService {

    @GET("review/{review_id}")
    Call<Review> getDetails(
            @Path("review_id") String id,
            @Query("api_key") String apiKey
    );
}