package org.michaelbel.movies.app.network

import com.chuckerteam.chucker.api.ChuckerInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import java.util.concurrent.TimeUnit
import javax.inject.Singleton
import okhttp3.OkHttpClient

@Module
@InstallIn(SingletonComponent::class)
object OkhttpModule {

    @Provides
    @Singleton
    fun provideOkHttp(
        chuckerInterceptor: ChuckerInterceptor
    ): OkHttpClient {
        val callTimeoutDuration: Pair<Long, TimeUnit> = 0L to TimeUnit.SECONDS // суммарное время на выполнение запроса (нет ограничений).
        val connectTimeoutDuration: Pair<Long, TimeUnit> = 10L to TimeUnit.SECONDS // время на подключение к заданному хосту.
        val readTimeoutDuration: Pair<Long, TimeUnit> = 10L to TimeUnit.SECONDS // время на получение ответа сервера.
        val writeTimeoutDuration: Pair<Long, TimeUnit> = 10L to TimeUnit.SECONDS // время на передачу запроса серверу.
        val builder = OkHttpClient.Builder().apply {
            addInterceptor(chuckerInterceptor)
            callTimeout(callTimeoutDuration.first, callTimeoutDuration.second)
            connectTimeout(connectTimeoutDuration.first, connectTimeoutDuration.second)
            readTimeout(readTimeoutDuration.first, readTimeoutDuration.second)
            writeTimeout(writeTimeoutDuration.first, writeTimeoutDuration.second)
        }
        return builder.build()
    }
}