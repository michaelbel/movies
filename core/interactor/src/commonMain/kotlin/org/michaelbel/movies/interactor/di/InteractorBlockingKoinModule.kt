package org.michaelbel.movies.interactor.di

import org.koin.core.module.Module
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import org.michaelbel.movies.analytics.di.moviesAnalyticsKoinModule
import org.michaelbel.movies.common.dispatchers.di.dispatchersKoinModule
import org.michaelbel.movies.interactor.AccountInteractor
import org.michaelbel.movies.interactor.AuthenticationInteractor
import org.michaelbel.movies.interactor.ImageInteractor
import org.michaelbel.movies.interactor.Interactor
import org.michaelbel.movies.interactor.LocaleInteractor
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
import org.michaelbel.movies.interactor.impl.LocaleInteractorImpl
import org.michaelbel.movies.persistence.database.di.moviesDatabaseKoinModule
import org.michaelbel.movies.repository.di.repositoryKoinModule

expect val interactorBlockingKoinModule: Module