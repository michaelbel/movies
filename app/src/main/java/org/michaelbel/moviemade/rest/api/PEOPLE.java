package org.michaelbel.moviemade.rest.api;

import org.michaelbel.moviemade.rest.model.Person;
import org.michaelbel.moviemade.rest.response.MoviePeopleResponse;
import org.michaelbel.moviemade.rest.response.PeopleResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface PEOPLE {

    @GET("person/{person_id}?")
    Observable<Person> getDetails(
        @Path("person_id") int id,
        @Query("api_key") String apiKey,
        @Query("language") String language,
        @Query("append_to_response") String response
    );

    @GET("person/{person_id}/movie_credits?")
    Observable<MoviePeopleResponse> getMovieCredits(
        @Path("person_id") int id,
        @Query("api_key") String apiKey,
        @Query("language") String language
    );

    @GET("person/{person_id}/tv_credits?")
    Observable<?> getTVCredits(
        @Path("person_id") int id,
        @Query("api_key") String apiKey,
        @Query("language") String language
    );

    @GET("person/{person_id}/combined_credits?")
    Observable<?> getCombinedCredits(
        @Path("person_id") int id,
        @Query("api_key") String apiKey,
        @Query("language") String language
    );

    @GET("person/{person_id}/external_ids?")
    Observable<?> getExternalIDs(
        @Path("person_id") int id,
        @Query("api_key") String apiKey,
        @Query("language") String language
    );

    @GET("person/{person_id}/images?")
    Observable<?> getImages(
        @Path("person_id") int id,
        @Query("api_key") String apiKey
    );

    @GET("person/{person_id}/tagged_images?")
    Observable<?> getTaggedImages(
        @Path("person_id") int id,
        @Query("api_key") String apiKey,
        @Query("page") int page
    );

    @GET("person/{person_id}/changes?")
    Observable<?> getChanges(
        @Path("person_id") int id,
        @Query("api_key") String apiKey,
        @Query("end_date") String endDate,
        @Query("start_date") String startDate,
        @Query("page") int page
    );

    @GET("person/latest?")
    Observable<?> getLatest(
        @Query("api_key") String apiKey,
        @Query("language") String language
    );

    @GET("person/popular?")
    Observable<PeopleResponse> getPopular(
        @Query("api_key") String apiKey,
        @Query("language") String language,
        @Query("page") int page
    );
}