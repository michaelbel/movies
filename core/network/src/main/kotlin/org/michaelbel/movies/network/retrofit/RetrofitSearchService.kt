package org.michaelbel.movies.network.retrofit

import org.michaelbel.movies.network.model.MovieResponse
import org.michaelbel.movies.network.model.Result
import retrofit2.http.GET
import retrofit2.http.Query

@Deprecated("Use KtorSearchService instead", ReplaceWith("KtorSearchService"))
interface RetrofitSearchService {

    @GET("search/movie")
    suspend fun searchMovies(
        @Query("query") query: String,
        @Query("language") language: String,
        @Query("page") page: Int
    ): Result<MovieResponse>
}