package org.michaelbel.movies.network.ktor.di

import io.ktor.client.HttpClient
import io.ktor.client.engine.darwin.Darwin
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.core.qualifier.named
import org.koin.dsl.module
import org.michaelbel.movies.network.config.TRAKT_API_ENDPOINT
import org.michaelbel.movies.network.config.TRAKT_API_VERSION
import org.michaelbel.movies.network.config.traktClientId

private const val REQUEST_TIMEOUT_MILLIS = 10_000L
private const val SOCKET_TIMEOUT_SECONDS = 10_000L
private const val CONNECT_TIMEOUT_MILLIS = 10_000L

actual val traktKtorKoinModule = module {
    single<HttpClient>(named("trakt")) {
        val ktor = HttpClient(Darwin) {
            defaultRequest {
                contentType(ContentType.Application.Json)
                url(TRAKT_API_ENDPOINT)
                headers.append("trakt-api-key", traktClientId)
                headers.append("trakt-api-version", TRAKT_API_VERSION)
            }
            install(ContentNegotiation) {
                json(Json { ignoreUnknownKeys = true })
            }
            install(HttpTimeout) {
                requestTimeoutMillis = REQUEST_TIMEOUT_MILLIS
                connectTimeoutMillis = CONNECT_TIMEOUT_MILLIS
                socketTimeoutMillis = SOCKET_TIMEOUT_SECONDS
            }
        }
        ktor
    }
}
