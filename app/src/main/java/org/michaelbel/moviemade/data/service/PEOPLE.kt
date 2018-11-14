package org.michaelbel.moviemade.data.service

import io.reactivex.Observable
import org.michaelbel.moviemade.data.dao.CreditsResponse
import org.michaelbel.moviemade.data.dao.Person
import org.michaelbel.moviemade.data.dao.PersonsResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PEOPLE {

    @GET("person/{person_id}?")
    fun getDetails(
        @Path("person_id") id: Int,
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("append_to_response") response: String
    ): Observable<Person>

    @GET("person/{person_id}/changes?")
    fun getChanges(
        @Path("person_id") id: Int,
        @Query("api_key") apiKey: String,
        @Query("end_date") endDate: String,
        @Query("start_date") startDate: String,
        @Query("page") page: Int
    ): Observable<*>

    @GET("person/{person_id}/movie_credits?")
    fun getMovieCredits(
        @Path("person_id") id: Int,
        @Query("api_key") apiKey: String,
        @Query("language") language: String
    ): Observable<CreditsResponse>

    @GET("person/{person_id}/tv_credits?")
    fun getTVCredits(
        @Path("person_id") id: Int,
        @Query("api_key") apiKey: String,
        @Query("language") language: String
    ): Observable<*>

    @GET("person/{person_id}/combined_credits?")
    fun getCombinedCredits(
        @Path("person_id") id: Int,
        @Query("api_key") apiKey: String,
        @Query("language") language: String
    ): Observable<*>

    @GET("person/{person_id}/external_ids?")
    fun getExternalIDs(
        @Path("person_id") id: Int,
        @Query("api_key") apiKey: String,
        @Query("language") language: String
    ): Observable<*>

    @GET("person/{person_id}/images?")
    fun getImages(
        @Path("person_id") id: Int,
        @Query("api_key") apiKey: String
    ): Observable<*>

    @GET("person/{person_id}/tagged_images?")
    fun getTaggedImages(
        @Path("person_id") id: Int,
        @Query("api_key") apiKey: String,
        @Query("page") page: Int
    ): Observable<*>

    // getTranslations

    @GET("person/latest?")
    fun getLatest(
        @Query("api_key") apiKey: String,
        @Query("language") language: String
    ): Observable<*>

    @GET("person/popular?")
    fun getPopular(
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("page") page: Int
    ): Observable<PersonsResponse>
}