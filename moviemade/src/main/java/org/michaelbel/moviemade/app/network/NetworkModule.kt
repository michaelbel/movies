package org.michaelbel.moviemade.app.network

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import org.michaelbel.moviemade.app.ktx.createService
import org.michaelbel.moviemade.app.TMDB_API_ENDPOINT
import org.michaelbel.moviemade.app.data.Api
import retrofit2.Converter
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideOkHttp(
        @ApplicationContext context: Context
    ): OkHttpClient {
        val callTimeoutDuration: Pair<Long, TimeUnit> = 0L to TimeUnit.SECONDS // суммарное время на выполнение запроса (нет ограничений).
        val connectTimeoutDuration: Pair<Long, TimeUnit> = 10L to TimeUnit.SECONDS // время на подключение к заданному хосту.
        val readTimeoutDuration: Pair<Long, TimeUnit> = 10L to TimeUnit.SECONDS // время на получение ответа сервера.
        val writeTimeoutDuration: Pair<Long, TimeUnit> = 10L to TimeUnit.SECONDS // время на передачу запроса серверу.
        val builder = OkHttpClient.Builder().apply {
            callTimeout(callTimeoutDuration.first, callTimeoutDuration.second)
            connectTimeout(connectTimeoutDuration.first, connectTimeoutDuration.second)
            readTimeout(readTimeoutDuration.first, readTimeoutDuration.second)
            writeTimeout(writeTimeoutDuration.first, writeTimeoutDuration.second)
        }
        return builder.build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(
        converterFactory: Converter.Factory,
        okHttpClient: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(TMDB_API_ENDPOINT)
            .addConverterFactory(converterFactory)
            .client(okHttpClient)
            .build()
    }

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): Api {
        return createService(retrofit)
    }

    /*private fun okHttpClient(context: Context): OkHttpClient {
        val okHttpClient = OkHttpClient().newBuilder()
        if (BuildConfig.DEBUG) {
            okHttpClient.interceptors().add(httpLoggingInterceptor)
            okHttpClient.networkInterceptors().add(StethoInterceptor())
        }
        return okHttpClient.build()
    }

    private val httpLoggingInterceptor: HttpLoggingInterceptor
        get() {
            val httpLoggingInterceptor = HttpLoggingInterceptor { message -> Timber.d(message) }
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            return httpLoggingInterceptor
        }*/
}