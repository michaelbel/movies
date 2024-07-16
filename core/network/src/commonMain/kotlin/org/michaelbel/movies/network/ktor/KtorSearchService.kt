package org.michaelbel.movies.network.ktor

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import org.michaelbel.movies.network.config.isNeedApiKeyQuery
import org.michaelbel.movies.network.config.tmdbApiKey
import org.michaelbel.movies.network.model.MovieResponse
import org.michaelbel.movies.network.model.Result

internal class KtorSearchService(
    private val ktorHttpClient: HttpClient
) {

    suspend fun searchMovies(
        query: String,
        language: String,
        page: Int
    ): Result<MovieResponse> {
        return ktorHttpClient.get("search/movie") {
            if (isNeedApiKeyQuery) {
                parameter("api_key", tmdbApiKey)
            }
            parameter("query", query)
            parameter("language", language)
            parameter("page", page)
        }.body()
    }
}