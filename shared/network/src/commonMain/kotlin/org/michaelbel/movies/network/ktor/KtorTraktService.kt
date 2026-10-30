package org.michaelbel.movies.network.ktor

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.HttpHeaders
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonNull
import kotlinx.serialization.json.jsonObject
import org.michaelbel.movies.network.model.TraktMovieResponse
import org.michaelbel.movies.network.model.TraktMoviesResult
import org.michaelbel.movies.network.model.TraktRefreshTokenRequest
import org.michaelbel.movies.network.model.TraktRevokeRequest
import org.michaelbel.movies.network.model.TraktSettingsResponse
import org.michaelbel.movies.network.model.TraktTokenRequest
import org.michaelbel.movies.network.model.TraktTokenResponse

class KtorTraktService(
    private val ktorHttpClient: HttpClient
) {

    private val json = Json { ignoreUnknownKeys = true }

    suspend fun movie(traktId: Int): TraktMovieResponse {
        val response = ktorHttpClient.get("movies/$traktId") {
            parameter("extended", "full")
        }
        return response.body()
    }

    suspend fun search(query: String, page: Int, limit: Int): TraktMoviesResult {
        val response = ktorHttpClient.get("search/movie") {
            parameter("query", query)
            parameter("page", page)
            parameter("limit", limit)
            parameter("extended", "full,images")
        }
        val array = response.body<JsonArray>()
        val movies = array.mapNotNull { element ->
            when (val movieElement = element.jsonObject["movie"]) {
                null, is JsonNull -> null
                else -> json.decodeFromJsonElement(TraktMovieResponse.serializer(), movieElement)
            }
        }
        val totalPages = response.headers["X-Pagination-Page-Count"]?.toIntOrNull() ?: page
        return TraktMoviesResult(
            page = page,
            totalPages = totalPages,
            results = movies
        )
    }

    suspend fun token(request: TraktTokenRequest): TraktTokenResponse {
        return ktorHttpClient.post("oauth/token") {
            setBody(request)
        }.body()
    }

    suspend fun refreshToken(request: TraktRefreshTokenRequest): TraktTokenResponse {
        return ktorHttpClient.post("oauth/token") {
            setBody(request)
        }.body()
    }

    suspend fun revokeToken(request: TraktRevokeRequest) {
        ktorHttpClient.post("oauth/revoke") {
            setBody(request)
        }
    }

    suspend fun settings(accessToken: String): TraktSettingsResponse {
        return ktorHttpClient.get("users/settings") {
            header(HttpHeaders.Authorization, "Bearer $accessToken")
        }.body()
    }

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
