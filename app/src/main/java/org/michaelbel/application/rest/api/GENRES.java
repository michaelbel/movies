package org.michaelbel.application.rest.api;

import org.michaelbel.application.rest.response.GenreResponse;
import org.michaelbel.application.rest.response.MovieGenresResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

@SuppressWarnings("all")
public interface GENRES {

    @GET("genre/movie/list?")
    Call<MovieGenresResponse> getMovieList(
            @Query("api_key") String apiKey,
            @Query("language") String language
    );

    @GET("genre/tv/list?")
    Call<?> getTVList(
            @Query("api_key") String apiKey,
            @Query("language") String language
    );

    @GET("genre/{genre_id}/movies?")
    Call<GenreResponse> getMovies(
            @Path("genre_id") int id,
            @Query("api_key") String apiKey,
            @Query("language") String language,
            @Query("include_adult") boolean adult,
            @Query("page") int page
    );
}