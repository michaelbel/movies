package org.michaelbel.movies.network.okhttp.di

import com.chuckerteam.chucker.api.ChuckerInterceptor
import io.github.rotbolt.flakerokhttpcore.FlakerInterceptor
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import org.michaelbel.movies.network.chucker.chuckerKoinModule
import org.michaelbel.movies.network.flaker.di.flakerKoinModule
import org.michaelbel.movies.network.okhttp.interceptor.ApikeyInterceptor
import org.michaelbel.movies.network_kmp.BuildConfig
import java.util.concurrent.TimeUnit

const val HTTP_CACHE_SIZE_BYTES = 1024 * 1024 * 50
const val CONNECT_TIMEOUT_MILLIS = 10_000L
private const val READ_TIMEOUT_MILLIS = 10_000L
private const val WRITE_TIMEOUT_MILLIS = 10_000L
private const val CALL_TIMEOUT_MILLIS = 0L

val okhttpKoinModule = module {
    includes(
        chuckerKoinModule,
        flakerKoinModule
    )
    single { Cache(androidContext().cacheDir, HTTP_CACHE_SIZE_BYTES.toLong()) }
    single {
        HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else level
        }
    }
    single { ApikeyInterceptor(BuildConfig.TMDB_API_KEY) }
    single {
        val builder = OkHttpClient.Builder().apply {
            addInterceptor(get<ChuckerInterceptor>())
            addInterceptor(get<FlakerInterceptor>())
            addInterceptor(get<HttpLoggingInterceptor>())
            addInterceptor(get<ApikeyInterceptor>())
            callTimeout(CALL_TIMEOUT_MILLIS, TimeUnit.MILLISECONDS)
            connectTimeout(CONNECT_TIMEOUT_MILLIS, TimeUnit.MILLISECONDS)
            readTimeout(READ_TIMEOUT_MILLIS, TimeUnit.MILLISECONDS)
            writeTimeout(WRITE_TIMEOUT_MILLIS, TimeUnit.MILLISECONDS)
            retryOnConnectionFailure(true)
            followRedirects(true)
            followSslRedirects(true)
            cache(get<Cache>())
        }
        builder.build()
    }
}