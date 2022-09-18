package org.michaelbel.movies.core

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import okhttp3.OkHttpClient
import retrofit2.Converter
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {

    private const val TMDB_API_ENDPOINT = "https://api.themoviedb.org/3/"

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
}