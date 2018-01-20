package org.michaelbel.moviemade.rest.api;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface CERTIFICATIONS {

    @GET("certification/movie/list?")
    Call<?> getMovieCertifications(
        @Query("api_key") String apiKey
    );

    @GET("certification/tv/list?")
    Call<?> getTVCertifications(
        @Query("api_key") String apiKey
    );
}