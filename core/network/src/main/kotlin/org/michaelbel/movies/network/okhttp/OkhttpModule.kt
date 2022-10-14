package org.michaelbel.movies.network.okhttp

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
internal object OkhttpModule {

    /**
     * Суммарное время на выполнение запроса (0 - нет ограничений).
     */
    private const val CALL_TIMEOUT_SECONDS = 0L

    /**
     * Время на подключение к заданному хосту.
     */
    private const val CONNECT_TIMEOUT_SECONDS = 10L

    /**
     * Время на получение ответа сервера.
     */
    private const val READ_TIMEOUT_SECONDS = 10L

    /**
     * Время на передачу запроса серверу.
     */
    private const val WRITE_TIMEOUT_SECONDS = 10L

    @Provides
    @Singleton
    fun provideOkHttp(
        chuckerInterceptor: ChuckerInterceptor
    ): OkHttpClient {
        val builder = OkHttpClient.Builder().apply {
            addInterceptor(chuckerInterceptor)
            callTimeout(CALL_TIMEOUT_SECONDS, TimeUnit.SECONDS)
            connectTimeout(CONNECT_TIMEOUT_SECONDS, TimeUnit.SECONDS)
            readTimeout(READ_TIMEOUT_SECONDS, TimeUnit.SECONDS)
            writeTimeout(WRITE_TIMEOUT_SECONDS, TimeUnit.SECONDS)
        }
        return builder.build()
    }
}