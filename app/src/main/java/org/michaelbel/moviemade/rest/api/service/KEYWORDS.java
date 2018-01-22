package org.michaelbel.moviemade.rest.api.service;

import org.michaelbel.moviemade.rest.model.v3.Keyword;
import org.michaelbel.moviemade.rest.response.MoviesResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface KEYWORDS {

    @GET("keyword/{keyword_id}?")
    Observable<Keyword> getDetails(
        @Path("keyword_id") int id,
        @Query("api_key") String apiKey
    );

    @GET("keyword/{keyword_id}/movies")
    Observable<MoviesResponse> getMovies(
        @Path("keyword_id") int id,
        @Query("api_key") String apiKey,
        @Query("language") String language,
        @Query("include_adult") boolean adult,
        @Query("page") int page
    );
}