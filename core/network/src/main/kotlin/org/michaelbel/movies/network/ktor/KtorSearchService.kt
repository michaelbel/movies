package org.michaelbel.movies.network.ktor

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import javax.inject.Inject
import org.michaelbel.movies.network.model.MovieResponse
import org.michaelbel.movies.network.model.Result

class KtorSearchService @Inject constructor(
    private val ktorHttpClient: HttpClient
) {

    suspend fun searchMovies(
        query: String,
        language: String,
        page: Int
    ): Result<MovieResponse> {
        return ktorHttpClient.get("search/movie") {
            parameter("query", query)
            parameter("language", language)
            parameter("page", page)
        }.body()
    }
}