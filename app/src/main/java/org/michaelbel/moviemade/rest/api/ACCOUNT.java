package org.michaelbel.moviemade.rest.api;

import org.michaelbel.moviemade.rest.model.Account;
import org.michaelbel.moviemade.rest.response.MovieResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ACCOUNT {

    @GET("account?")
    Call<Account> getDetails(
            @Query("api_key") String apiKey,
            @Query("session_id") String sessionId
    );

    @GET("account/{account_id}/lists?")
    Call<?> getCreatedLists(
            @Path("account_id") int id,
            @Query("api_key") String apiKey,
            @Query("session_id") String sessionId,
            @Query("language") String language
    );

    @GET("account/{account_id}/favorite/movies?")
    Call<MovieResponse> getFavoriteMovies(
            @Path("account_id") int accountId,
            @Query("api_key") String apiKey,
            @Query("session_id") String sessionId,
            @Query("language") String lang,
            @Query("sort_by") String sort
    );

    @GET("account/{account_id}/favorite/tv?")
    Call<MovieResponse> getFavoriteTVShows(
            @Path("account_id") int accountId,
            @Query("api_key") String apiKey,
            @Query("session_id") String sessionId,
            @Query("language") String lang,
            @Query("sort_by") String sort
    );

    @POST("account/{account_id}/favorite?")
    Call<?> markAsFavorite(
            @Path("account_id") int id,
            @Query("api_key") String apiKey,
            @Query("session_id") String sessionId
    );

    @GET("account/{account_id}/rated/movies?")
    Call<?> getRatedMovies(
            @Path("account_id") int id,
            @Query("api_key") String apiKey,
            @Query("session_id") String sessionId,
            @Query("language") String language,
            @Query("sort_by") String sort
    );

    @GET("account/{account_id}/rated/tv?")
    Call<?> getRatedTVShows(
            @Path("account_id") int id,
            @Query("api_key") String apiKey,
            @Query("session_id") String sessionId,
            @Query("language") String language,
            @Query("sort_by") String sort
    );

    @GET("account/{account_id}/rated/tv/episodes?")
    Call<?> getRatedTVEpisodes(
            @Path("account_id") int id,
            @Query("api_key") String apiKey,
            @Query("session_id") String sessionId,
            @Query("language") String language,
            @Query("sort_by") String sort
    );

    @GET("account/{account_id}/watching/movies?")
    Call<MovieResponse> getMovieWatchlist(
            @Path("account_id") int id,
            @Query("api_key") String apiKey,
            @Query("session_id") String sessionId,
            @Query("language") String language,
            @Query("sort_by") String sort
    );

    @GET("account/{account_id}/watching/tv?")
    Call<MovieResponse> getTVShowsWatchlist(
            @Path("account_id") int id,
            @Query("api_key") String apiKey,
            @Query("session_id") String sessionId,
            @Query("language") String language,
            @Query("sort_by") String sort
    );

    @POST("account/{account_id}/watching?")
    Call<?> addToWatchlist(
            @Path("account_id") int id,
            @Query("api_key") String apiKey,
            @Query("session_id") String sessionId
    );
}