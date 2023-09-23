package org.michaelbel.movies.network.service.movie

import org.michaelbel.movies.network.model.Movie
import org.michaelbel.movies.network.model.MovieResponse
import org.michaelbel.movies.network.model.Result
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieService {

    @GET("movie/{movie_id}")
    suspend fun movie(
        @Path("movie_id") id: Int,
        @Query("api_key") apiKey: String,
        @Query("language") language: String
    ): Movie

    @GET("movie/{list}")
    suspend fun movies(
        @Path("list") list: String,
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("page") page: Int
    ): Result<MovieResponse>
}