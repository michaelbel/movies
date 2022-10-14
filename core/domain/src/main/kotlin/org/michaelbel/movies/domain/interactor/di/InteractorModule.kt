package org.michaelbel.movies.domain.interactor.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import org.michaelbel.movies.domain.interactor.MovieInteractor
import org.michaelbel.movies.domain.interactor.SettingsInteractor
import org.michaelbel.movies.domain.interactor.impl.MovieInteractorImpl
import org.michaelbel.movies.domain.interactor.impl.SettingsInteractorImpl

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