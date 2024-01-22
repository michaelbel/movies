package org.michaelbel.movies.interactor.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import org.michaelbel.movies.interactor.AccountInteractor
import org.michaelbel.movies.interactor.AuthenticationInteractor
import org.michaelbel.movies.interactor.ImageInteractor
import org.michaelbel.movies.interactor.MovieInteractor
import org.michaelbel.movies.interactor.NotificationInteractor
import org.michaelbel.movies.interactor.PagingKeyInteractor
import org.michaelbel.movies.interactor.SearchInteractor
import org.michaelbel.movies.interactor.SettingsInteractor
import org.michaelbel.movies.interactor.SuggestionInteractor
import org.michaelbel.movies.interactor.impl.AccountInteractorImpl
import org.michaelbel.movies.interactor.impl.AuthenticationInteractorImpl
import org.michaelbel.movies.interactor.impl.ImageInteractorImpl
import org.michaelbel.movies.interactor.impl.MovieInteractorImpl
import org.michaelbel.movies.interactor.impl.NotificationInteractorImpl
import org.michaelbel.movies.interactor.impl.PagingKeyInteractorImpl
import org.michaelbel.movies.interactor.impl.SearchInteractorImpl
import org.michaelbel.movies.interactor.impl.SettingsInteractorImpl
import org.michaelbel.movies.interactor.impl.SuggestionInteractorImpl

@Module
@InstallIn(SingletonComponent::class)
internal interface InteractorModule {

    @Binds
    @Singleton
    fun provideAccountInteractor(
        interactor: AccountInteractorImpl
    ): AccountInteractor

    @Binds
    @Singleton
    fun provideAuthenticationInteractor(
        interactor: AuthenticationInteractorImpl
    ): AuthenticationInteractor

    @Binds
    @Singleton
    fun provideImageInteractor(
        interactor: ImageInteractorImpl
    ): ImageInteractor

    @Binds
    @Singleton
    fun provideMovieInteractor(
        interactor: MovieInteractorImpl
    ): MovieInteractor

    @Binds
    @Singleton
    fun provideNotificationInteractor(
        interactor: NotificationInteractorImpl
    ): NotificationInteractor

    @Binds
    @Singleton
    fun providePagingKeyInteractor(
        interactor: PagingKeyInteractorImpl
    ): PagingKeyInteractor

    @Binds
    @Singleton
    fun provideSearchInteractor(
        interactor: SearchInteractorImpl
    ): SearchInteractor

    @Binds
    @Singleton
    fun provideSettingsInteractor(
        interactor: SettingsInteractorImpl
    ): SettingsInteractor

    @Binds
    @Singleton
    fun provideSuggestionInteractor(
        interactor: SuggestionInteractorImpl
    ): SuggestionInteractor
}