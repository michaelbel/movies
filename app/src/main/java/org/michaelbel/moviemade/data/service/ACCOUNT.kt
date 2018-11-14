package org.michaelbel.moviemade.data.service

import io.reactivex.Observable
import org.michaelbel.moviemade.data.dao.Account
import org.michaelbel.moviemade.data.dao.MoviesResponse
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ACCOUNT {

    @GET("account?")
    fun getDetails(
        @Query("api_key") apiKey: String,
        @Query("session_id") sessionId: String
    ): Observable<Account>

    @GET("account/{account_id}/lists?")
    fun getCreatedLists(
        @Path("account_id") id: Int,
        @Query("api_key") apiKey: String,
        @Query("session_id") sessionId: String,
        @Query("language") language: String
    ): Observable<*>

    @GET("account/{account_id}/favorite/parts?")
    fun getFavoriteMovies(
        @Path("account_id") accountId: Int,
        @Query("api_key") apiKey: String,
        @Query("session_id") sessionId: String,
        @Query("language") lang: String,
        @Query("sort_by") sort: String
    ): Observable<MoviesResponse>

    @GET("account/{account_id}/favorite/tv?")
    fun getFavoriteTVShows(
        @Path("account_id") accountId: Int,
        @Query("api_key") apiKey: String,
        @Query("session_id") sessionId: String,
        @Query("language") lang: String,
        @Query("sort_by") sort: String
    ): Observable<MoviesResponse>

    @POST("account/{account_id}/favorite?")
    fun markAsFavorite(
        @Path("account_id") id: Int,
        @Query("api_key") apiKey: String,
        @Query("session_id") sessionId: String
    ): Observable<*>

    @GET("account/{account_id}/rated/parts?")
    fun getRatedMovies(
        @Path("account_id") id: Int,
        @Query("api_key") apiKey: String,
        @Query("session_id") sessionId: String,
        @Query("language") language: String,
        @Query("sort_by") sort: String
    ): Observable<*>

    @GET("account/{account_id}/rated/tv?")
    fun getRatedTVShows(
        @Path("account_id") id: Int,
        @Query("api_key") apiKey: String,
        @Query("session_id") sessionId: String,
        @Query("language") language: String,
        @Query("sort_by") sort: String
    ): Observable<*>

    @GET("account/{account_id}/rated/tv/episodes?")
    fun getRatedTVEpisodes(
        @Path("account_id") id: Int,
        @Query("api_key") apiKey: String,
        @Query("session_id") sessionId: String,
        @Query("language") language: String,
        @Query("sort_by") sort: String
    ): Observable<*>

    @GET("account/{account_id}/watching/parts?")
    fun getMovieWatchlist(
        @Path("account_id") id: Int,
        @Query("api_key") apiKey: String,
        @Query("session_id") sessionId: String,
        @Query("language") language: String,
        @Query("sort_by") sort: String
    ): Observable<MoviesResponse>

    @GET("account/{account_id}/watching/tv?")
    fun getTVShowsWatchlist(
        @Path("account_id") id: Int,
        @Query("api_key") apiKey: String,
        @Query("session_id") sessionId: String,
        @Query("language") language: String,
        @Query("sort_by") sort: String
    ): Observable<MoviesResponse>

    @POST("account/{account_id}/watching?")
    fun addToWatchlist(
        @Path("account_id") id: Int,
        @Query("api_key") apiKey: String,
        @Query("session_id") sessionId: String
    ): Observable<*>
}