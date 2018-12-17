package org.michaelbel.moviemade.data.service

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface ChangesService {

    @GET("movie/changes?")
    fun getMovieChangeList(
        @Query("api_key") apiKey: String,
        @Query("end_date") endDate: String,
        @Query("start_date") startDate: String,
        @Query("page") page: Int
    ): Observable<*>

    @GET("tv/changes?")
    fun getTVChangeList(
        @Query("api_key") apiKey: String,
        @Query("end_date") endDate: String,
        @Query("start_date") startDate: String,
        @Query("page") page: Int
    ): Observable<*>

    @GET("person/changes?")
    fun getPersonChangeList(
        @Query("api_key") apiKey: String,
        @Query("end_date") endDate: String,
        @Query("start_date") startDate: String,
        @Query("page") page: Int
    ): Observable<*>
}