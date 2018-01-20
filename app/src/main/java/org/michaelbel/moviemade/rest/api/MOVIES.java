package org.michaelbel.moviemade.rest.api;

import org.michaelbel.moviemade.rest.response.KeywordResponse;
import org.michaelbel.moviemade.rest.model.Movie;
import org.michaelbel.moviemade.rest.response.CreditResponse;
import org.michaelbel.moviemade.rest.response.ImageResponse;
import org.michaelbel.moviemade.rest.response.MovieResponse;
import org.michaelbel.moviemade.rest.response.ReviewResponse;
import org.michaelbel.moviemade.rest.response.TrailersResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MOVIES {

    @GET("movie/{movie_id}?")
    Call<Movie> getDetails(
        @Path("movie_id") int id,
        @Query("api_key") String apiKey,
        @Query("language") String language,
        @Query("append_to_response") String response
    );

    @GET("movie/{movie_id}/account_states?")
    Call<?> getAccountStates(
        @Path("movie_id") int id,
        @Query("api_key") String apiKey,
        @Query("session_id") String sessionId,
        @Query("quest_session_id") String questSessionId
    );

    @GET("movie/{movie_id}/changes?")
    Call<?> getChanges(
        @Path("movie_id") int id,
        @Query("api_key") String apiKey,
        @Query("start_date") String startDate,
        @Query("end_date") String endDate,
        @Query("page") int page
    );

    @GET("movie/{movie_id}/alternative_titles?")
    Call<?> getAlternativeTitles(
        @Path("movie_id") int id,
        @Query("api_key") String apiKey,
        @Query("country") String country
    );

    @GET("movie/{movie_id}/credits?")
    Call<CreditResponse> getCredits(
        @Path("movie_id") int id,
        @Query("api_key") String apiKey
    );

    @GET("movie/{movie_id}/images?")
    Call<ImageResponse> getImages(
        @Path("movie_id") int id,
        @Query("api_key") String apiKey,
        @Query("include_image_language") String lang
    );

    @GET("movie/{movie_id}/keywords?")
    Call<KeywordResponse> getKeywords(
        @Path("movie_id") int id,
        @Query("api_key") String apiKey
    );

    @GET("movie/{movie_id}/release_dates?")
    Call<?> getReleaseDates(
        @Path("movie_id") int id,
        @Query("api_key") String apiKey
    );

    @GET("movie/{movie_id}/videos?")
    Call<TrailersResponse> getVideos(
        @Path("movie_id") int id,
        @Query("api_key") String apiKey,
        @Query("language") String lang
    );

    @GET("movie/{movie_id}/translations?")
    Call<?> getTranslations(
        @Query("api_key") String apiKey,
        @Query("language") String lang
    );

    @GET("movie/{movie_id}/recommendations?")
    Call<MovieResponse> getRecommendations(
        @Path("movie_id") int id,
        @Query("api_key") String apiKey,
        @Query("language") String language,
        @Query("page") int page
    );

    @GET("movie/{movie_id}/similar?")
    Call<MovieResponse> getSimilar(
        @Path("movie_id") int id,
        @Query("api_key") String apiKey,
        @Query("language") String language,
        @Query("page") int page
    );

    @GET("movie/{movie_id}/reviews?")
    Call<ReviewResponse> getReviews(
        @Path("movie_id") int id,
        @Query("api_key") String apiKey,
        @Query("language") String language,
        @Query("page") int page
    );

    @GET("movie/{movie_id}/lists?")
    Call<MovieResponse> getLists(
        @Path("movie_id") String param,
        @Query("api_key") String apiKey,
        @Query("language") String language,
        @Query("page") int page
    );

    // rateMovie

    // deleteRating

    @GET("movie/latest?")
    Call<?> getLatest(
        @Query("api_key") String apiKey,
        @Query("language") String lang
    );

    @GET("movie/now_playing?")
    Call<MovieResponse> getNowPlaying(
        @Query("api_key") String apiKey,
        @Query("language") String lang,
        @Query("page") int page
    );

    @GET("movie/popular?")
    Call<MovieResponse> getPopular(
        @Query("api_key") String apiKey,
        @Query("language") String lang,
        @Query("page") int page
    );

    @GET("movie/top_rated?")
    Call<MovieResponse> getTopRated(
        @Query("api_key") String apiKey,
        @Query("language") String lang,
        @Query("page") int page
    );

    @GET("movie/upcoming?")
    Call<MovieResponse> getUpcoming(
        @Query("api_key") String apiKey,
        @Query("language") String lang,
        @Query("page") int page
    );
}