package org.michaelbel.moviemade.rest.api;

import org.michaelbel.moviemade.data.dao.Movie;
import org.michaelbel.moviemade.data.dao.VideosResponse;
import org.michaelbel.moviemade.rest.response.CreditResponse;
import org.michaelbel.moviemade.rest.response.ImageResponse;
import org.michaelbel.moviemade.rest.response.KeywordResponse;
import org.michaelbel.moviemade.rest.response.MovieResponse;
import org.michaelbel.moviemade.rest.response.ReviewResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MOVIES {

    @GET("movie/{movie_id}?")
    Observable<Movie> getDetails(
        @Path("movie_id") int id,
        @Query("api_key") String apiKey,
        @Query("language") String language,
        @Query("append_to_response") String response
    );

    @GET("movie/{movie_id}/account_states?")
    Observable<?> getAccountStates(
        @Path("movie_id") int id,
        @Query("api_key") String apiKey,
        @Query("session_id") String sessionId,
        @Query("quest_session_id") String questSessionId
    );

    @GET("movie/{movie_id}/changes?")
    Observable<?> getChanges(
        @Path("movie_id") int id,
        @Query("api_key") String apiKey,
        @Query("start_date") String startDate,
        @Query("end_date") String endDate,
        @Query("page") int page
    );

    @GET("movie/{movie_id}/alternative_titles?")
    Observable<?> getAlternativeTitles(
        @Path("movie_id") int id,
        @Query("api_key") String apiKey,
        @Query("country") String country
    );

    @GET("movie/{movie_id}/credits?")
    Observable<CreditResponse> getCredits(
        @Path("movie_id") int id,
        @Query("api_key") String apiKey
    );

    @GET("movie/{movie_id}/images?")
    Observable<ImageResponse> getImages(
        @Path("movie_id") int id,
        @Query("api_key") String apiKey,
        @Query("include_image_language") String lang
    );

    @GET("movie/{movie_id}/keywords?")
    Observable<KeywordResponse> getKeywords(
        @Path("movie_id") int id,
        @Query("api_key") String apiKey
    );

    @GET("movie/{movie_id}/release_dates?")
    Observable<?> getReleaseDates(
        @Path("movie_id") int id,
        @Query("api_key") String apiKey
    );

    @GET("movie/{movie_id}/videos?")
    Observable<VideosResponse> getVideos(
        @Path("movie_id") int id,
        @Query("api_key") String apiKey,
        @Query("language") String lang
    );

    @GET("movie/{movie_id}/translations?")
    Observable<?> getTranslations(
        @Query("api_key") String apiKey,
        @Query("language") String lang
    );

    @GET("movie/{movie_id}/recommendations?")
    Observable<MovieResponse> getRecommendations(
        @Path("movie_id") int id,
        @Query("api_key") String apiKey,
        @Query("language") String language,
        @Query("page") int page
    );

    @GET("movie/{movie_id}/similar?")
    Observable<MovieResponse> getSimilar(
        @Path("movie_id") int id,
        @Query("api_key") String apiKey,
        @Query("language") String language,
        @Query("page") int page
    );

    @GET("movie/{movie_id}/reviews?")
    Observable<ReviewResponse> getReviews(
        @Path("movie_id") int id,
        @Query("api_key") String apiKey,
        @Query("language") String language,
        @Query("page") int page
    );

    @GET("movie/{movie_id}/lists?")
    Observable<MovieResponse> getLists(
        @Path("movie_id") String param,
        @Query("api_key") String apiKey,
        @Query("language") String language,
        @Query("page") int page
    );

    // rateMovie

    // deleteRating

    @GET("movie/latest?")
    Observable<?> getLatest(
        @Query("api_key") String apiKey,
        @Query("language") String lang
    );

    @GET("movie/now_playing?")
    Observable<MovieResponse> getNowPlaying(
        @Query("api_key") String apiKey,
        @Query("language") String lang,
        @Query("page") int page
    );

    @GET("movie/popular?")
    Observable<MovieResponse> getPopular(
        @Query("api_key") String apiKey,
        @Query("language") String lang,
        @Query("page") int page
    );

    @GET("movie/top_rated?")
    Observable<MovieResponse> getTopRated(
        @Query("api_key") String apiKey,
        @Query("language") String lang,
        @Query("page") int page
    );

    @GET("movie/upcoming?")
    Observable<MovieResponse> getUpcoming(
        @Query("api_key") String apiKey,
        @Query("language") String lang,
        @Query("page") int page
    );
}