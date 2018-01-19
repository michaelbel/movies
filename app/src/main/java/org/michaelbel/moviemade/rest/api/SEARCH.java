package org.michaelbel.moviemade.rest.api;

import org.michaelbel.moviemade.rest.response.CollectionResponse;
import org.michaelbel.moviemade.rest.response.CompanyResponse;
import org.michaelbel.moviemade.rest.response.KeywordsResponse;
import org.michaelbel.moviemade.rest.response.MovieResponse;
import org.michaelbel.moviemade.rest.response.PeopleResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface SEARCH {

    @GET("search/multi?")
    Call<?> searchMulti();

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

    @GET("search/tv?")
    Call<?> searchTvShows();

    @GET("search/person?")
    Call<PeopleResponse> searchPeople(
            @Query("api_key") String apiKey,
            @Query("language") String lang,
            @Query("query") String query,
            @Query("page") int page,
            @Query("include_adult") boolean adult,
            @Query("region") String region
    );

    @GET("search/keyword?")
    Call<KeywordsResponse> searchKeywords(
            @Query("api_key") String apiKey,
            @Query("query") String query,
            @Query("page") int page
    );

    @GET("search/collection?")
    Call<CollectionResponse> searchCollections(
        @Query("api_key") String apiKey,
        @Query("language") String lang,
        @Query("query") String query,
        @Query("page") int page
    );

    @GET("search/company?")
    Call<CompanyResponse> searchCompanies(
        @Query("api_key") String apiKey,
        @Query("query") String query,
        @Query("page") int page
    );
}