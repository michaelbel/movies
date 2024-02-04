package org.michaelbel.movies.network.service.trakt.movie

import kotlinx.serialization.json.JsonElement
import retrofit2.http.GET

interface TraktMovieService {

    @GET("movies/trending")
    suspend fun moviesTrending(): JsonElement

    @GET("movies/popular")
    suspend fun moviesPopular(): JsonElement

    @GET("movies/anticipated")
    suspend fun moviesAnticipated(): JsonElement

    @GET("movies/boxoffice")
    suspend fun moviesBoxoffice(): JsonElement
}