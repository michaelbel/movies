package org.michaelbel.moviemade.rest.api;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface FIND {

    @GET("find/{external_id}?")
    Observable<?> findById(
        @Path("external_id") String id,
        @Query("api_key") String apiKey,
        @Query("language") String language,
        @Query("external_source") String ex_source
    );
}