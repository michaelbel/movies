package org.michaelbel.moviemade.rest.api;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface CREDITS {

    @GET("credit/{credit_id}?")
    Call<?> getDetails(
        @Path("credit_id") String id,
        @Query("api_key") String apiKey
    );
}