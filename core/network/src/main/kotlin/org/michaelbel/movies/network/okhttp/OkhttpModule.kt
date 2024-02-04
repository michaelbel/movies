package org.michaelbel.movies.network.okhttp

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.github.rotbolt.flakerokhttpcore.FlakerInterceptor
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.michaelbel.movies.network.BuildConfig
import org.michaelbel.movies.network.okhttp.interceptor.TmdbApiInterceptor
import org.michaelbel.movies.network.okhttp.interceptor.TraktApiInterceptor
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object OkhttpModule {

    @Provides
    @Singleton
    fun httpCache(
        @ApplicationContext context: Context
    ): Cache {
        return Cache(context.cacheDir, HTTP_CACHE_SIZE_BYTES)
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
    fun provideTmdbApiInterceptor(): TmdbApiInterceptor {
        return TmdbApiInterceptor(BuildConfig.TMDB_API_KEY)
    }

    @Provides
    @Singleton
    fun provideTraktApiInterceptor(): TraktApiInterceptor {
        return TraktApiInterceptor(BuildConfig.TRAKT_CLIENT_ID)
    }

    @Provides
    @Singleton
    fun provideOkHttp(
        chuckerInterceptor: ChuckerInterceptor,
        flakerInterceptor: FlakerInterceptor,
        httpLoggingInterceptor: HttpLoggingInterceptor,
        cache: Cache
    ): OkHttpClient {
        val builder = OkHttpClient.Builder().apply {
            addInterceptor(chuckerInterceptor)
            addInterceptor(flakerInterceptor)
            addInterceptor(httpLoggingInterceptor)
            callTimeout(CALL_TIMEOUT_SECONDS, TimeUnit.SECONDS)
            connectTimeout(CONNECT_TIMEOUT_SECONDS, TimeUnit.SECONDS)
            readTimeout(READ_TIMEOUT_SECONDS, TimeUnit.SECONDS)
            writeTimeout(WRITE_TIMEOUT_SECONDS, TimeUnit.SECONDS)
            retryOnConnectionFailure(true)
            followRedirects(true)
            followSslRedirects(true)
            cache(cache)
        }
        return builder.build()
    }

    private const val HTTP_CACHE_SIZE_BYTES = 1024 * 1024 * 50L
    private const val CALL_TIMEOUT_SECONDS = 0L
    private const val CONNECT_TIMEOUT_SECONDS = 10L
    private const val READ_TIMEOUT_SECONDS = 10L
    private const val WRITE_TIMEOUT_SECONDS = 10L
}