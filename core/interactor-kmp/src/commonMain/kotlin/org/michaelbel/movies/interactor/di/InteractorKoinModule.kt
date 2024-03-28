package org.michaelbel.movies.interactor.di

import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import org.michaelbel.movies.analytics.di.moviesAnalyticsKoinModule
import org.michaelbel.movies.common.dispatchers.di.dispatchersKoinModule
import org.michaelbel.movies.interactor.AccountInteractor
import org.michaelbel.movies.interactor.AuthenticationInteractor
import org.michaelbel.movies.interactor.ImageInteractor
import org.michaelbel.movies.interactor.Interactor
import org.michaelbel.movies.interactor.MovieInteractor
import org.michaelbel.movies.interactor.NotificationInteractor
import org.michaelbel.movies.interactor.SearchInteractor
import org.michaelbel.movies.interactor.SettingsInteractor
import org.michaelbel.movies.interactor.SuggestionInteractor
import org.michaelbel.movies.interactor.impl.AccountInteractorImpl
import org.michaelbel.movies.interactor.impl.AuthenticationInteractorImpl
import org.michaelbel.movies.interactor.impl.ImageInteractorImpl
import org.michaelbel.movies.interactor.impl.MovieInteractorImpl
import org.michaelbel.movies.interactor.impl.NotificationInteractorImpl
import org.michaelbel.movies.interactor.impl.SearchInteractorImpl
import org.michaelbel.movies.interactor.impl.SettingsInteractorImpl
import org.michaelbel.movies.interactor.impl.SuggestionInteractorImpl
import org.michaelbel.movies.persistence.database.di.moviesDatabaseKoinModule
import org.michaelbel.movies.repository.di.repositoryKoinModule

val interactorKoinModule = module {
    includes(
        dispatchersKoinModule,
        repositoryKoinModule,
        moviesDatabaseKoinModule,
        moviesAnalyticsKoinModule
    )
    singleOf(::AccountInteractorImpl) { bind<AccountInteractor>() }
    singleOf(::AuthenticationInteractorImpl) { bind<AuthenticationInteractor>() }
    singleOf(::ImageInteractorImpl) { bind<ImageInteractor>() }
    singleOf(::MovieInteractorImpl) { bind<MovieInteractor>() }
    singleOf(::NotificationInteractorImpl) { bind<NotificationInteractor>() }
    singleOf(::SearchInteractorImpl) { bind<SearchInteractor>() }
    singleOf(::SettingsInteractorImpl) { bind<SettingsInteractor>() }
    singleOf(::SuggestionInteractorImpl) { bind<SuggestionInteractor>() }
    single { Interactor(get(), get(), get(), get(), get(), get(), get(), get()) }
}