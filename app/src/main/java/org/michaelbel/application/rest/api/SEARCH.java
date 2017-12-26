package org.michaelbel.application.rest.api;

import org.michaelbel.application.rest.response.MovieResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

@SuppressWarnings("all")
public interface SEARCH {

    // searchCompanies

    // searchCollections

    // searchKeywords

    @GET("search/{param}?")
    Call<MovieResponse> searchMovies(
            @Path("param") String param,
            @Query("api_key") String apiKey,
            @Query("language") String lang,
            @Query("query") String query
    );

    // multiSearch

    // searchPeople

    // searchTvShows
}