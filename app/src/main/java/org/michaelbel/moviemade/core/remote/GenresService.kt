package org.michaelbel.moviemade.core.remote

import io.reactivex.Observable
import org.michaelbel.moviemade.core.entity.GenresResponse
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