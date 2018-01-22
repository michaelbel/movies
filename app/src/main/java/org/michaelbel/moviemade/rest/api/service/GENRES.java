package org.michaelbel.moviemade.rest.api.service;

import org.michaelbel.moviemade.rest.response.GenresResponse;
import org.michaelbel.moviemade.rest.response.MoviesResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface GENRES {

    @GET("genre/movie/list?")
    Observable<GenresResponse> getMovieList(
        @Query("api_key") String apiKey,
        @Query("language") String language
    );

    @GET("genre/tv/list?")
    Observable<GenresResponse> getTVList(
        @Query("api_key") String apiKey,
        @Query("language") String language
    );

    @GET("genre/{genre_id}/movies?")
    Observable<MoviesResponse> getMovies(
        @Path("genre_id") int id,
        @Query("api_key") String apiKey,
        @Query("language") String language,
        @Query("include_adult") boolean adult,
        @Query("page") int page
    );
}