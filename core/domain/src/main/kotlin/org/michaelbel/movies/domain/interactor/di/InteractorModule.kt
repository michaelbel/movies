package org.michaelbel.movies.domain.interactor.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.michaelbel.movies.domain.interactor.account.impl.AccountInteractorImpl
import org.michaelbel.movies.domain.interactor.authentication.impl.AuthenticationInteractorImpl
import org.michaelbel.movies.domain.interactor.movie.impl.MovieInteractorImpl
import org.michaelbel.movies.domain.interactor.settings.impl.SettingsInteractorImpl
import org.michaelbel.movies.interactor.AccountInteractor
import org.michaelbel.movies.interactor.AuthenticationInteractor
import org.michaelbel.movies.interactor.MovieInteractor
import org.michaelbel.movies.interactor.SettingsInteractor
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal interface InteractorModule {

    @Binds
    @Singleton
    fun provideMovieInteractor(
        interactor: MovieInteractorImpl
    ): MovieInteractor

    @Binds
    @Singleton
    fun provideAuthenticationInteractor(
        interactor: AuthenticationInteractorImpl
    ): AuthenticationInteractor

    @Binds
    @Singleton
    fun provideAccountInteractor(
        interactor: AccountInteractorImpl
    ): AccountInteractor

    @Binds
    @Singleton
    fun provideSettingsInteractor(
        interactor: SettingsInteractorImpl
    ): SettingsInteractor
}