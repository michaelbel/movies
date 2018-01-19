package org.michaelbel.moviemade.rest.api.service;

import org.michaelbel.moviemade.rest.TmdbService;
import org.michaelbel.moviemade.rest.model.Keyword;
import org.michaelbel.moviemade.rest.response.MoviesResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface KEYWORDS extends TmdbService {

    @GET("keyword/{keyword_id}?")
    Call<Keyword> getDetails(
        @Path("keyword_id") int id,
        @Query("api_key") String apiKey
    );

    @GET("keyword/{keyword_id}/movies")
    Call<MoviesResponse> getMovies(
        @Path("keyword_id") int id,
        @Query("api_key") String apiKey,
        @Query("language") String language,
        @Query("include_adult") boolean adult,
        @Query("page") int page
    );
}