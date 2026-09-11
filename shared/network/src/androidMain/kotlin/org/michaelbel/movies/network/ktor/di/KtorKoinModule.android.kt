package org.michaelbel.movies.network.ktor.di

import com.chuckerteam.chucker.api.ChuckerInterceptor
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import org.michaelbel.movies.network.chuckerKoinModule
import org.michaelbel.movies.network.config.TMDB_API_ENDPOINT
import org.michaelbel.movies.network.config.tmdbApiKey
import org.michaelbel.movies.network.connectivity.di.connectivityKoinModule
import org.michaelbel.movies.network.connectivity.impl.BackendConnectivityInterceptor
import org.michaelbel.movies.network.httpLoggingInterceptorKoinModule

private const val REQUEST_TIMEOUT_MILLIS = 10_000L
private const val SOCKET_TIMEOUT_SECONDS = 10_000L
private const val HTTP_CACHE_SIZE_BYTES = 1024 * 1024 * 50
private const val CONNECT_TIMEOUT_MILLIS = 10_000L

actual val ktorKoinModule = module {
    includes(
        connectivityKoinModule,
        chuckerKoinModule,
        httpLoggingInterceptorKoinModule
    )
    single<HttpClient> {
        val ktor = HttpClient(OkHttp) {
            defaultRequest {
                contentType(ContentType.Application.Json)
                url(TMDB_API_ENDPOINT)
                url {
                    parameters.append("api_key", tmdbApiKey)
                }
            }
            install(ContentNegotiation) {
                json(Json { ignoreUnknownKeys = true })
            }
            install(HttpTimeout) {
                requestTimeoutMillis = REQUEST_TIMEOUT_MILLIS
                connectTimeoutMillis = CONNECT_TIMEOUT_MILLIS
                socketTimeoutMillis = SOCKET_TIMEOUT_SECONDS
            }
            engine {
                clientCacheSize = HTTP_CACHE_SIZE_BYTES
                config {
                    // Observe only real backend transport failures. The interceptor rethrows
                    // the original IOException immediately and diagnostics run asynchronously.
                    addInterceptor(get<BackendConnectivityInterceptor>())
                    addInterceptor(get<ChuckerInterceptor>())
                    addInterceptor(get<HttpLoggingInterceptor>())
                }
            }
        }
        ktor
    }
}
