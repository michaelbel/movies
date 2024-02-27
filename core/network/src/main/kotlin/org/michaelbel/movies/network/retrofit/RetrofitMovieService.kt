package org.michaelbel.movies.network.retrofit

import org.michaelbel.movies.network.model.ImagesResponse
import org.michaelbel.movies.network.model.Movie
import org.michaelbel.movies.network.model.MovieResponse
import org.michaelbel.movies.network.model.Result
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

@Deprecated("Use KtorMovieService instead", ReplaceWith("KtorMovieService"))
interface RetrofitMovieService {

    @GET("movie/{list}")
    suspend fun movies(
        @Path("list") list: String,
        @Query("language") language: String,
        @Query("page") page: Int
    ): Result<MovieResponse>

    @GET("movie/{movie_id}")
    suspend fun movie(
        @Path("movie_id") id: Int,
        @Query("language") language: String
    ): Movie

    @GET("movie/{movie_id}/images")
    suspend fun images(
        @Path("movie_id") id: Int
    ): ImagesResponse
}