package org.michaelbel.movies.network.retrofit.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import okhttp3.OkHttpClient
import org.michaelbel.movies.network.TMDB_API_ENDPOINT
import org.michaelbel.movies.network.retrofit.RetrofitAccountService
import org.michaelbel.movies.network.retrofit.RetrofitAuthenticationService
import org.michaelbel.movies.network.retrofit.RetrofitMovieService
import org.michaelbel.movies.network.retrofit.RetrofitSearchService
import org.michaelbel.movies.network.retrofit.ktx.createService
import retrofit2.Converter
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
internal object RetrofitModule {

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
    fun provideAuthenticationService(
        retrofit: Retrofit
    ): RetrofitAuthenticationService = retrofit.createService()

    @Provides
    @Singleton
    fun provideAccountService(
        retrofit: Retrofit
    ): RetrofitAccountService = retrofit.createService()

    @Provides
    @Singleton
    fun provideMovieService(
        retrofit: Retrofit
    ): RetrofitMovieService = retrofit.createService()

    @Provides
    @Singleton
    fun provideSearchService(
        retrofit: Retrofit
    ): RetrofitSearchService = retrofit.createService()
}