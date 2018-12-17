package org.michaelbel.moviemade.data.service

import io.reactivex.Observable
import org.michaelbel.moviemade.data.entity.GenresResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface GenresService {

    @GET("genre/movie/list?")
    fun getMovieList(
        @Query("api_key") apiKey: String,
        @Query("language") language: String
    ): Observable<GenresResponse>

    @GET("genre/tv/list?")
    fun getTVList(
        @Query("api_key") apiKey: String,
        @Query("language") language: String
    ): Observable<GenresResponse>
}