package org.michaelbel.moviemade.rest.api;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface CHANGES {

    @GET("movie/changes?")
    Observable<?> getMovieChangeList(
        @Query("api_key") String apiKey,
        @Query("end_date") String endDate,
        @Query("start_date") String startDate,
        @Query("page") int page
    );

    @GET("tv/changes?")
    Observable<?> getTVChangeList(
        @Query("api_key") String apiKey,
        @Query("end_date") String endDate,
        @Query("start_date") String startDate,
        @Query("page") int page
    );

    @GET("person/changes?")
    Observable<?> getPersonChangeList(
        @Query("api_key") String apiKey,
        @Query("end_date") String endDate,
        @Query("start_date") String startDate,
        @Query("page") int page
    );
}