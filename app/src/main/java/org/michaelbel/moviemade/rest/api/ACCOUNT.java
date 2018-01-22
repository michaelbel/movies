package org.michaelbel.moviemade.rest.api;

import org.michaelbel.moviemade.rest.model.Account;
import org.michaelbel.moviemade.rest.response.MovieResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ACCOUNT {

    @GET("account?")
    Observable<Account> getDetails(
        @Query("api_key") String apiKey,
        @Query("session_id") String sessionId
    );

    @GET("account/{account_id}/lists?")
    Observable<?> getCreatedLists(
        @Path("account_id") int id,
        @Query("api_key") String apiKey,
        @Query("session_id") String sessionId,
        @Query("language") String language
    );

    @GET("account/{account_id}/favorite/movies?")
    Observable<MovieResponse> getFavoriteMovies(
        @Path("account_id") int accountId,
        @Query("api_key") String apiKey,
        @Query("session_id") String sessionId,
        @Query("language") String lang,
        @Query("sort_by") String sort
    );

    @GET("account/{account_id}/favorite/tv?")
    Observable<MovieResponse> getFavoriteTVShows(
        @Path("account_id") int accountId,
        @Query("api_key") String apiKey,
        @Query("session_id") String sessionId,
        @Query("language") String lang,
        @Query("sort_by") String sort
    );

    @POST("account/{account_id}/favorite?")
    Observable<?> markAsFavorite(
        @Path("account_id") int id,
        @Query("api_key") String apiKey,
        @Query("session_id") String sessionId
    );

    @GET("account/{account_id}/rated/movies?")
    Observable<?> getRatedMovies(
        @Path("account_id") int id,
        @Query("api_key") String apiKey,
        @Query("session_id") String sessionId,
        @Query("language") String language,
        @Query("sort_by") String sort
    );

    @GET("account/{account_id}/rated/tv?")
    Observable<?> getRatedTVShows(
        @Path("account_id") int id,
        @Query("api_key") String apiKey,
        @Query("session_id") String sessionId,
        @Query("language") String language,
        @Query("sort_by") String sort
    );

    @GET("account/{account_id}/rated/tv/episodes?")
    Observable<?> getRatedTVEpisodes(
        @Path("account_id") int id,
        @Query("api_key") String apiKey,
        @Query("session_id") String sessionId,
        @Query("language") String language,
        @Query("sort_by") String sort
    );

    @GET("account/{account_id}/watching/movies?")
    Observable<MovieResponse> getMovieWatchlist(
        @Path("account_id") int id,
        @Query("api_key") String apiKey,
        @Query("session_id") String sessionId,
        @Query("language") String language,
        @Query("sort_by") String sort
    );

    @GET("account/{account_id}/watching/tv?")
    Observable<MovieResponse> getTVShowsWatchlist(
        @Path("account_id") int id,
        @Query("api_key") String apiKey,
        @Query("session_id") String sessionId,
        @Query("language") String language,
        @Query("sort_by") String sort
    );

    @POST("account/{account_id}/watching?")
    Observable<?> addToWatchlist(
        @Path("account_id") int id,
        @Query("api_key") String apiKey,
        @Query("session_id") String sessionId
    );
}