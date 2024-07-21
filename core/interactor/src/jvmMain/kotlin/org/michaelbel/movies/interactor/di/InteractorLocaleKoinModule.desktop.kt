package org.michaelbel.movies.interactor.di

import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import org.michaelbel.movies.analytics.di.moviesAnalyticsKoinModule
import org.michaelbel.movies.common.dispatchers.di.dispatchersKoinModule
import org.michaelbel.movies.interactor.LocaleInteractor
import org.michaelbel.movies.interactor.impl.LocaleInteractorImpl
import org.michaelbel.movies.persistence.database.di.moviesDatabaseKoinModule
import org.michaelbel.movies.repository.di.repositoryBlockingKoinModule

actual val interactorLocaleKoinModule = module {
    includes(
        dispatchersKoinModule,
        repositoryBlockingKoinModule,
        moviesDatabaseKoinModule,
        moviesAnalyticsKoinModule
    )
    singleOf(::LocaleInteractorImpl) { bind<LocaleInteractor>() }
}