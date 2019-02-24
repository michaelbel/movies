package org.michaelbel.moviemade.core.remote

import io.reactivex.Observable
import org.michaelbel.moviemade.core.entity.*
import retrofit2.http.*

interface AccountService {

    @GET("account?")
    fun getDetails(
        @Query("api_key") apiKey: String,
        @Query("session_id") sessionId: String
    ): Observable<Account>

    @GET("account/{account_id}/favorite/movies?")
    fun getFavoriteMovies(
        @Path("account_id") accountId: Int,
        @Query("api_key") apiKey: String,
        @Query("session_id") sessionId: String,
        @Query("language") lang: String,
        @Query("sort_by") sort: String,
        @Query("page") page: Int
    ): Observable<MoviesResponse>

    @POST("account/{account_id}/favorite?")
    fun markAsFavorite(
        @Header("Content-Type") contentType: String,
        @Path("account_id") id: Int,
        @Query("api_key") apiKey: String,
        @Query("session_id") sessionId: String,
        @Body fave: Fave
    ): Observable<Mark>

    @GET("account/{account_id}/watchlist/movies?")
    fun getWatchlistMovies(
        @Path("account_id") id: Int,
        @Query("api_key") apiKey: String,
        @Query("session_id") sessionId: String,
        @Query("language") language: String,
        @Query("sort_by") sort: String,
        @Query("page") page: Int
    ): Observable<MoviesResponse>

    @POST("account/{account_id}/watchlist?")
    fun addToWatchlist(
        @Header("Content-Type") contentType: String,
        @Path("account_id") id: Int,
        @Query("api_key") apiKey: String,
        @Query("session_id") sessionId: String,
        @Body watch: Watch
    ): Observable<Mark>

    @GET("account/{account_id}/favorite/tv?")
    fun getFavoriteTVShows(
        @Path("account_id") accountId: Int,
        @Query("api_key") apiKey: String,
        @Query("session_id") sessionId: String,
        @Query("language") lang: String,
        @Query("sort_by") sort: String
    ): Observable<MoviesResponse>

    @GET("account/{account_id}/lists?")
    fun getCreatedLists(
        @Path("account_id") id: Int,
        @Query("api_key") apiKey: String,
        @Query("session_id") sessionId: String,
        @Query("language") language: String
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

    @GET("account/{account_id}/watching/tv?")
    fun getTVShowsWatchlist(
        @Path("account_id") id: Int,
        @Query("api_key") apiKey: String,
        @Query("session_id") sessionId: String,
        @Query("language") language: String,
        @Query("sort_by") sort: String
    ): Observable<MoviesResponse>
}