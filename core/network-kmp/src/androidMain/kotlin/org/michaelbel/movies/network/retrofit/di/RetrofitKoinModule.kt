package org.michaelbel.movies.network.retrofit.di

import okhttp3.OkHttpClient
import org.koin.dsl.module
import org.michaelbel.movies.network.config.TMDB_API_ENDPOINT
import org.michaelbel.movies.network.okhttp.di.okhttpKoinModule
import org.michaelbel.movies.network.serialization.converterFactoryKoinModule
import retrofit2.Converter
import retrofit2.Retrofit

val retrofitKoinModule = module {
    includes(
        converterFactoryKoinModule,
        okhttpKoinModule
    )
    single {
        Retrofit.Builder()
            .baseUrl(TMDB_API_ENDPOINT)
            .addConverterFactory(get<Converter.Factory>())
            .client(get<OkHttpClient>())
            .build()
    }
}