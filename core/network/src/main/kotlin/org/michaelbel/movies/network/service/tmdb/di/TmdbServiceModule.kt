package org.michaelbel.movies.network.service.tmdb.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.michaelbel.movies.network.retrofit.ktx.createService
import org.michaelbel.movies.network.service.tmdb.account.TmdbAccountService
import org.michaelbel.movies.network.service.tmdb.authentication.TmdbAuthenticationService
import org.michaelbel.movies.network.service.tmdb.movie.TmdbMovieService
import org.michaelbel.movies.network.service.tmdb.search.TmdbSearchService
import retrofit2.Retrofit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object TmdbServiceModule {

    @Provides
    @Singleton
    fun provideTmdbAuthenticationService(
        @Named("TMDB") retrofit: Retrofit
    ): TmdbAuthenticationService = retrofit.createService()

    @Provides
    @Singleton
    fun provideTmdbAccountService(
        @Named("TMDB") retrofit: Retrofit
    ): TmdbAccountService = retrofit.createService()

    @Provides
    @Singleton
    fun provideTmdbMovieService(
        @Named("TMDB") retrofit: Retrofit
    ): TmdbMovieService = retrofit.createService()

    @Provides
    @Singleton
    fun provideTmdbSearchService(
        @Named("TMDB") retrofit: Retrofit
    ): TmdbSearchService = retrofit.createService()
}