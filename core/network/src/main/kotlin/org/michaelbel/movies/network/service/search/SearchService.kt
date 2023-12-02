package org.michaelbel.movies.network.service.search

import org.michaelbel.movies.network.model.MovieResponse
import org.michaelbel.movies.network.model.Result
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchService {

    @GET("search/movie")
    suspend fun searchMovies(
        @Query("query") query: String,
        @Query("language") language: String,
        @Query("page") page: Int
    ): Result<MovieResponse>
}