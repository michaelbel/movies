package org.michaelbel.movies.repository.di

import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import org.michaelbel.movies.network.di.networkKoinModule
import org.michaelbel.movies.persistence.database.di.persistenceBlockingKoinModule
import org.michaelbel.movies.persistence.database.di.persistenceKoinModule
import org.michaelbel.movies.persistence.datastore.di.moviesPreferencesKoinModule
import org.michaelbel.movies.repository.AccountRepository
import org.michaelbel.movies.repository.AuthenticationRepository
import org.michaelbel.movies.repository.ImageRepository
import org.michaelbel.movies.repository.MovieRepository
import org.michaelbel.movies.repository.NotificationRepository
import org.michaelbel.movies.repository.PagingKeyRepository
import org.michaelbel.movies.repository.SearchRepository
import org.michaelbel.movies.repository.SettingsRepository
import org.michaelbel.movies.repository.SuggestionRepository
import org.michaelbel.movies.repository.impl.AccountRepositoryImpl
import org.michaelbel.movies.repository.impl.AuthenticationRepositoryImpl
import org.michaelbel.movies.repository.impl.ImageRepositoryImpl
import org.michaelbel.movies.repository.impl.MovieRepositoryImpl
import org.michaelbel.movies.repository.impl.NotificationRepositoryImpl
import org.michaelbel.movies.repository.impl.PagingKeyRepositoryImpl
import org.michaelbel.movies.repository.impl.SearchRepositoryImpl
import org.michaelbel.movies.repository.impl.SettingsRepositoryImpl
import org.michaelbel.movies.repository.impl.SuggestionRepositoryImpl

val repositoryKoinModule = module {
    includes(
        networkKoinModule,
        persistenceKoinModule,
        persistenceBlockingKoinModule,
        moviesPreferencesKoinModule,
    )
    singleOf(::AccountRepositoryImpl) { bind<AccountRepository>() }
    singleOf(::AuthenticationRepositoryImpl) { bind<AuthenticationRepository>() }
    singleOf(::ImageRepositoryImpl) { bind<ImageRepository>() }
    singleOf(::MovieRepositoryImpl) { bind<MovieRepository>() }
    singleOf(::NotificationRepositoryImpl) { bind<NotificationRepository>() }
    singleOf(::PagingKeyRepositoryImpl) { bind<PagingKeyRepository>() }
    singleOf(::SearchRepositoryImpl) { bind<SearchRepository>() }
    singleOf(::SettingsRepositoryImpl) { bind<SettingsRepository>() }
    singleOf(::SuggestionRepositoryImpl) { bind<SuggestionRepository>() }
}