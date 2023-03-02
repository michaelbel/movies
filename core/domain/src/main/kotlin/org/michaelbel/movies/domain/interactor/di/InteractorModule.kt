package org.michaelbel.movies.domain.interactor.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.michaelbel.movies.domain.interactor.movie.MovieInteractor
import org.michaelbel.movies.domain.interactor.movie.impl.MovieInteractorImpl
import org.michaelbel.movies.domain.interactor.settings.SettingsInteractor
import org.michaelbel.movies.domain.interactor.settings.impl.SettingsInteractorImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal interface InteractorModule {

    @Binds
    @Singleton
    fun provideMovieInteractor(interactor: MovieInteractorImpl): MovieInteractor

    @Binds
    @Singleton
    fun provideSettingsInteractor(interactor: SettingsInteractorImpl): SettingsInteractor
}