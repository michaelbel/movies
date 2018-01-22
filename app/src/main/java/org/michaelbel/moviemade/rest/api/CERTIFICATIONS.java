package org.michaelbel.moviemade.rest.api;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface CERTIFICATIONS {

    @GET("certification/movie/list?")
    Observable<?> getMovieCertifications(
        @Query("api_key") String apiKey
    );

    @GET("certification/tv/list?")
    Observable<?> getTVCertifications(
        @Query("api_key") String apiKey
    );
}