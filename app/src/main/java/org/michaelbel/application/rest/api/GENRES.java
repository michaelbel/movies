package org.michaelbel.application.rest.api;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

@SuppressWarnings("all")
public interface GENRES {

    @GET("genre/movie/list?")
    Call<?> getMovieList(
            @Query("api_key") String apiKey,
            @Query("language") String language
    );

    @GET("genre/tv/list?")
    Call<?> getTVList(
            @Query("api_key") String apiKey,
            @Query("language") String language
    );

    @GET("genre/{genre_id}/movies?")
    Call<?> getMovies(
            @Query("api_key") String apiKey,
            @Query("language") String language,
            @Query("include_adult") boolean adult
    );
}