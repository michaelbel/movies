package org.michaelbel.movies.network.okhttp.di

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.github.rotbolt.flakerokhttpcore.FlakerInterceptor
import java.util.concurrent.TimeUnit
import javax.inject.Singleton
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.michaelbel.movies.network.BuildConfig
import org.michaelbel.movies.network.okhttp.interceptor.ApikeyInterceptor

@Module
@InstallIn(SingletonComponent::class)
internal object OkhttpModule {

    @Provides
    @Singleton
    fun httpCache(
        @ApplicationContext context: Context
    ): Cache {
        return Cache(context.cacheDir, HTTP_CACHE_SIZE_BYTES.toLong())
    }

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else level
        }
    }

    @Provides
    @Singleton
    fun provideApikeyInterceptor(): ApikeyInterceptor {
        return ApikeyInterceptor(BuildConfig.TMDB_API_KEY)
    }

    @Provides
    @Singleton
    fun provideOkHttp(
        chuckerInterceptor: ChuckerInterceptor,
        flakerInterceptor: FlakerInterceptor,
        httpLoggingInterceptor: HttpLoggingInterceptor,
        apikeyInterceptor: ApikeyInterceptor,
        cache: Cache
    ): OkHttpClient {
        val builder = OkHttpClient.Builder().apply {
            addInterceptor(chuckerInterceptor)
            addInterceptor(flakerInterceptor)
            addInterceptor(httpLoggingInterceptor)
            addInterceptor(apikeyInterceptor)
            callTimeout(CALL_TIMEOUT_MILLIS, TimeUnit.MILLISECONDS)
            connectTimeout(CONNECT_TIMEOUT_MILLIS, TimeUnit.MILLISECONDS)
            readTimeout(READ_TIMEOUT_MILLIS, TimeUnit.MILLISECONDS)
            writeTimeout(WRITE_TIMEOUT_MILLIS, TimeUnit.MILLISECONDS)
            retryOnConnectionFailure(true)
            followRedirects(true)
            followSslRedirects(true)
            cache(cache)
        }
        return builder.build()
    }

    const val HTTP_CACHE_SIZE_BYTES = 1024 * 1024 * 50
    const val CONNECT_TIMEOUT_MILLIS = 10_000L
    private const val READ_TIMEOUT_MILLIS = 10_000L
    private const val WRITE_TIMEOUT_MILLIS = 10_000L
    private const val CALL_TIMEOUT_MILLIS = 0L
}