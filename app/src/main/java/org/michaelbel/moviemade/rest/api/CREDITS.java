package org.michaelbel.moviemade.rest.api;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface CREDITS {

    @GET("credit/{credit_id}?")
    Observable<?> getDetails(
        @Path("credit_id") String id,
        @Query("api_key") String apiKey
    );
}