package org.michaelbel.movies.network.retrofit

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import org.michaelbel.movies.network.okhttp.interceptor.TmdbApiInterceptor
import org.michaelbel.movies.network.okhttp.interceptor.TraktApiInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object RetrofitModule {

    private const val TMDB_API_ENDPOINT = "https://api.themoviedb.org/3/"
    private const val TRAKT_API_ENDPOINT = "https://api.trakt.tv/"

    @Provides
    @Singleton
    @Named("TMDB")
    fun provideTmdbRetrofit(
        converterFactory: Converter.Factory,
        tmdbApiInterceptor: TmdbApiInterceptor,
        okHttpClient: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(TMDB_API_ENDPOINT)
            .addConverterFactory(converterFactory)
            .client(okHttpClient.newBuilder().addInterceptor(tmdbApiInterceptor).build())
            .build()
    }

    @Provides
    @Singleton
    @Named("TRAKT")
    fun provideTraktRetrofit(
        converterFactory: Converter.Factory,
        traktApiInterceptor: TraktApiInterceptor,
        okHttpClient: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(TRAKT_API_ENDPOINT)
            .addConverterFactory(converterFactory)
            .client(okHttpClient.newBuilder().addInterceptor(traktApiInterceptor).build())
            .build()
    }
}