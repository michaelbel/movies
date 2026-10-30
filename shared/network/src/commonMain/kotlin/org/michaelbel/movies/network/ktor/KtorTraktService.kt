package org.michaelbel.movies.network.ktor

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.jsonObject
import org.michaelbel.movies.network.model.TraktMovieResponse
import org.michaelbel.movies.network.model.TraktMoviesResult

class KtorTraktService(
    private val ktorHttpClient: HttpClient
) {

    private val json = Json { ignoreUnknownKeys = true }

    suspend fun movies(list: String, page: Int, limit: Int): TraktMoviesResult {
        val response = ktorHttpClient.get("movies/$list") {
            parameter("page", page)
            parameter("limit", limit)
            parameter("extended", "full")
        }
        val array = response.body<JsonArray>()
        val movies = array.map { element ->
            val movieElement = element.jsonObject["movie"] ?: element
            json.decodeFromJsonElement(TraktMovieResponse.serializer(), movieElement)
        }
        val totalPages = response.headers["X-Pagination-Page-Count"]?.toIntOrNull() ?: page
        return TraktMoviesResult(
            page = page,
            totalPages = totalPages,
            results = movies
        )
    }
}
