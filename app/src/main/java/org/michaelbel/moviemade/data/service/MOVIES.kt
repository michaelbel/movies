package org.michaelbel.moviemade.data.service

import io.reactivex.Observable
import org.michaelbel.moviemade.data.dao.*
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MOVIES {

    @GET("movie/{movie_id}?")
    fun getDetails(
        @Path("movie_id") id: Int,
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("append_to_response") appendToResponse: String
    ): Observable<Movie>

    @GET("movie/{movie_id}/account_states?")
    fun getAccountStates(
        @Path("movie_id") id: Int,
        @Query("api_key") apiKey: String,
        @Query("session_id") sessionId: String,
        @Query("guest_session_id") guestSessionId: String
    ): Observable<AccountStates>

    @GET("movie/{movie_id}/changes?")
    fun getChanges(
        @Path("movie_id") id: Int,
        @Query("api_key") apiKey: String,
        @Query("start_date") startDate: String,
        @Query("end_date") endDate: String,
        @Query("page") page: Int
    ): Observable<*>

    @GET("movie/{movie_id}/alternative_titles?")
    fun getAlternativeTitles(
        @Path("movie_id") id: Int,
        @Query("api_key") apiKey: String,
        @Query("country") country: String
    ): Observable<*>

    @GET("movie/{movie_id}/credits?")
    fun getCredits(
        @Path("movie_id") id: Int,
        @Query("api_key") apiKey: String
    ): Observable<CreditsResponse>

    @GET("movie/{movie_id}/images?")
    fun getImages(
        @Path("movie_id") id: Int,
        @Query("api_key") apiKey: String,
        @Query("include_image_language") lang: String
    ): Observable<ImagesResponse>

    @GET("movie/{movie_id}/keywords?")
    fun getKeywords(
        @Path("movie_id") id: Int,
        @Query("api_key") apiKey: String
    ): Observable<KeywordsResponse>

    @GET("movie/{movie_id}/release_dates?")
    fun getReleaseDates(
        @Path("movie_id") id: Int,
        @Query("api_key") apiKey: String
    ): Observable<*>

    @GET("movie/{movie_id}/videos?")
    fun getVideos(
        @Path("movie_id") id: Int,
        @Query("api_key") apiKey: String,
        @Query("language") lang: String
    ): Observable<VideosResponse>

    @GET("movie/{movie_id}/translations?")
    fun getTranslations(
        @Query("api_key") apiKey: String,
        @Query("language") lang: String
    ): Observable<*>

    @GET("movie/{movie_id}/recommendations?")
    fun getRecommendations(
        @Path("movie_id") id: Int,
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("page") page: Int
    ): Observable<MoviesResponse>

    @GET("movie/{movie_id}/similar?")
    fun getSimilar(
        @Path("movie_id") id: Int,
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("page") page: Int
    ): Observable<MoviesResponse>

    @GET("movie/{movie_id}/reviews?")
    fun getReviews(
        @Path("movie_id") id: Int,
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("page") page: Int
    ): Observable<ReviewsResponse>

    @GET("movie/{movie_id}/lists?")
    fun getLists(
        @Path("movie_id") param: String,
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("page") page: Int
    ): Observable<MoviesResponse>

    // rateMovie

    // deleteRating

    @GET("movie/latest?")
    fun getLatest(
        @Query("api_key") apiKey: String,
        @Query("language") lang: String
    ): Observable<*>

    @GET("movie/now_playing?")
    fun getNowPlaying(
        @Query("api_key") apiKey: String,
        @Query("language") lang: String,
        @Query("page") page: Int
    ): Observable<MoviesResponse>

    /*@GET("movie/popular?")
    fun getPopular(
        @Query("api_key") apiKey: String,
        @Query("language") lang: String,
        @Query("page") page: Int
    ): Observable<MoviesResponse>*/

    @GET("movie/top_rated?")
    fun getTopRated(
        @Query("api_key") apiKey: String,
        @Query("language") lang: String,
        @Query("page") page: Int
    ): Observable<MoviesResponse>

    @GET("movie/upcoming?")
    fun getUpcoming(
        @Query("api_key") apiKey: String,
        @Query("language") lang: String,
        @Query("page") page: Int
    ): Observable<MoviesResponse>
}