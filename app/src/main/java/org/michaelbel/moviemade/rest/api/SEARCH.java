package org.michaelbel.moviemade.rest.api;

import org.michaelbel.moviemade.rest.response.MovieResponse;
import org.michaelbel.moviemade.rest.response.PeopleResponce;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface SEARCH {

    @GET("")
    Call<?> searchCompanies();

    @GET("")
    Call<?> searchCollections();

    @GET("")
    Call<?> searchKeywords();

    @GET("search/movie?")
    Call<MovieResponse> searchMovies(
            @Query("api_key") String apiKey,
            @Query("language") String lang,
            @Query("query") String query,
            @Query("page") int page,
            @Query("include_adult") boolean adult,
            @Query("region") String region
            //@Query("year") int year,
            //@Query("primary_release_year") int primaryReleaseYear
    );

    @GET("")
    Call<?> searchMulti();

    @GET("search/people?")
    Call<PeopleResponce> searchPeople(
            @Query("api_key") String apiKey,
            @Query("language") String lang,
            @Query("query") String query,
            @Query("page") int page,
            @Query("include_adult") boolean adult,
            @Query("region") String region
    );

    @GET("")
    Call<?> searchTvShows();
}