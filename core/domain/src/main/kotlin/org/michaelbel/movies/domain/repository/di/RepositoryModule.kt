package org.michaelbel.movies.domain.repository.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import org.michaelbel.movies.domain.repository.MovieRepository
import org.michaelbel.movies.domain.repository.SettingsRepository
import org.michaelbel.movies.domain.repository.impl.MovieRepositoryImpl
import org.michaelbel.movies.domain.repository.impl.SettingsRepositoryImpl

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    @Singleton
    fun provideMovieRepository(repository: MovieRepositoryImpl): MovieRepository

    @Binds
    @Singleton
    fun provideSettingsRepository(repository: SettingsRepositoryImpl): SettingsRepository
}