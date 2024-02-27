package org.michaelbel.movies.network.ktor.di

import com.chuckerteam.chucker.api.ChuckerInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.github.rotbolt.flakerokhttpcore.FlakerInterceptor
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import javax.inject.Singleton
import kotlinx.serialization.json.Json
import okhttp3.logging.HttpLoggingInterceptor
import org.michaelbel.movies.network.TMDB_API_ENDPOINT
import org.michaelbel.movies.network.okhttp.di.OkhttpModule
import org.michaelbel.movies.network.okhttp.interceptor.ApikeyInterceptor

@Module
@InstallIn(SingletonComponent::class)
internal object KtorModule {

    @Provides
    @Singleton
    fun provideKtorHttpClient(
        chuckerInterceptor: ChuckerInterceptor,
        flakerInterceptor: FlakerInterceptor,
        httpLoggingInterceptor: HttpLoggingInterceptor,
        apikeyInterceptor: ApikeyInterceptor
    ): HttpClient {
        val ktor = HttpClient(OkHttp) {
            defaultRequest {
                contentType(ContentType.Application.Json)
                url(TMDB_API_ENDPOINT)
            }
            install(ContentNegotiation) {
                json(
                    Json {
                        ignoreUnknownKeys = true
                    }
                )
            }
            install(HttpTimeout) {
                requestTimeoutMillis = REQUEST_TIMEOUT_MILLIS
                connectTimeoutMillis = OkhttpModule.CONNECT_TIMEOUT_MILLIS
                socketTimeoutMillis = SOCKET_TIMEOUT_SECONDS
            }
            engine {
                clientCacheSize = OkhttpModule.HTTP_CACHE_SIZE_BYTES
                config {
                    addInterceptor(chuckerInterceptor)
                    addInterceptor(flakerInterceptor)
                    addInterceptor(httpLoggingInterceptor)
                    addInterceptor(apikeyInterceptor)
                }
            }
        }
        return ktor
    }

    private const val REQUEST_TIMEOUT_MILLIS = 10_000L
    private const val SOCKET_TIMEOUT_SECONDS = 10_000L
}