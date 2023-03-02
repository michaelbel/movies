package org.michaelbel.movies.domain.repository.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import org.michaelbel.movies.domain.repository.movie.MovieRepository
import org.michaelbel.movies.domain.repository.settings.SettingsRepository
import org.michaelbel.movies.domain.repository.movie.impl.MovieRepositoryImpl
import org.michaelbel.movies.domain.repository.settings.impl.SettingsRepositoryImpl

@Module
@InstallIn(SingletonComponent::class)
internal interface RepositoryModule {

    @Binds
    @Singleton
    fun provideMovieRepository(repository: MovieRepositoryImpl): MovieRepository

    @Binds
    @Singleton
    fun provideSettingsRepository(repository: SettingsRepositoryImpl): SettingsRepository
}