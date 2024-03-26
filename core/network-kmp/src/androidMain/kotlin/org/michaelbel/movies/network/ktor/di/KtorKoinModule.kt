package org.michaelbel.movies.network.ktor.di

import com.chuckerteam.chucker.api.ChuckerInterceptor
import io.github.rotbolt.flakerokhttpcore.FlakerInterceptor
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
import org.michaelbel.movies.network.chucker.chuckerKoinModule
import org.michaelbel.movies.network.config.TMDB_API_ENDPOINT
import org.michaelbel.movies.network.flaker.di.flakerKoinModule
import org.michaelbel.movies.network.okhttp.di.CONNECT_TIMEOUT_MILLIS
import org.michaelbel.movies.network.okhttp.di.HTTP_CACHE_SIZE_BYTES
import org.michaelbel.movies.network.okhttp.di.okhttpKoinModule
import org.michaelbel.movies.network.okhttp.interceptor.ApikeyInterceptor

private const val REQUEST_TIMEOUT_MILLIS = 10_000L
private const val SOCKET_TIMEOUT_SECONDS = 10_000L

val ktorKoinModule = module {
    includes(
        chuckerKoinModule,
        flakerKoinModule,
        okhttpKoinModule
    )
    single {
        val ktor = HttpClient(OkHttp) {
            defaultRequest {
                contentType(ContentType.Application.Json)
                url(TMDB_API_ENDPOINT)
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
                    addInterceptor(get<ChuckerInterceptor>())
                    addInterceptor(get<FlakerInterceptor>())
                    addInterceptor(get<HttpLoggingInterceptor>())
                    addInterceptor(get<ApikeyInterceptor>())
                }
            }
        }
        ktor
    }
}