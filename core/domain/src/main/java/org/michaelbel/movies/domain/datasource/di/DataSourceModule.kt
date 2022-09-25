package org.michaelbel.movies.domain.datasource.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import org.michaelbel.movies.domain.datasource.MovieApi
import org.michaelbel.movies.domain.datasource.ktx.createService
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {

    @Provides
    @Singleton
    fun provideMovieApi(retrofit: Retrofit): MovieApi {
        return retrofit.createService()
    }
}