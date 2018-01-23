package org.michaelbel.moviemade.rest.api;

import org.michaelbel.moviemade.rest.response.CollectionResponse;
import org.michaelbel.moviemade.rest.response.CompanyResponse;
import org.michaelbel.moviemade.rest.response.KeywordsResponse;
import org.michaelbel.moviemade.rest.response.MovieResponse;
import org.michaelbel.moviemade.rest.response.PeopleResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface SEARCH {

    @GET("search/multi?")
    Observable<?> searchMulti(

    );

    @GET("search/movie?")
    Observable<MovieResponse> searchMovies(
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
    Observable<?> searchTvShows(

    );

    @GET("search/person?")
    Observable<PeopleResponse> searchPeople(
        @Query("api_key") String apiKey,
        @Query("language") String lang,
        @Query("query") String query,
        @Query("page") int page,
        @Query("include_adult") boolean adult,
        @Query("region") String region
    );

    @GET("search/keyword?")
    Observable<KeywordsResponse> searchKeywords(
        @Query("api_key") String apiKey,
        @Query("query") String query,
        @Query("page") int page
    );

    @GET("search/collection?")
    Observable<CollectionResponse> searchCollections(
        @Query("api_key") String apiKey,
        @Query("language") String lang,
        @Query("query") String query,
        @Query("page") int page
    );

    @GET("search/company?")
    Observable<CompanyResponse> searchCompanies(
        @Query("api_key") String apiKey,
        @Query("query") String query,
        @Query("page") int page
    );
}