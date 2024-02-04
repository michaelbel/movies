package org.michaelbel.movies.network.service.trakt.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.michaelbel.movies.network.retrofit.ktx.createService
import org.michaelbel.movies.network.service.trakt.movie.TraktMovieService
import retrofit2.Retrofit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object TraktServiceModule {

    @Provides
    @Singleton
    fun provideTraktMovieService(
        @Named("TRAKT") retrofit: Retrofit
    ): TraktMovieService = retrofit.createService()
}