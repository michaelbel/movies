package org.michaelbel.moviemade.rest.api;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface CHANGES {

    @GET("movie/changes?")
    Call<?> getMovieChangeList(
        @Query("api_key") String apiKey,
        @Query("end_date") String endDate,
        @Query("start_date") String startDate,
        @Query("page") int page
    );

    @GET("tv/changes?")
    Call<?> getTVChangeList(
        @Query("api_key") String apiKey,
        @Query("end_date") String endDate,
        @Query("start_date") String startDate,
        @Query("page") int page
    );

    @GET("person/changes?")
    Call<?> getPersonChangeList(
        @Query("api_key") String apiKey,
        @Query("end_date") String endDate,
        @Query("start_date") String startDate,
        @Query("page") int page
    );
}