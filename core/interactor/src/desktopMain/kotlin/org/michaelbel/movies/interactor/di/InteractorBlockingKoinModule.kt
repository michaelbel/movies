package org.michaelbel.movies.interactor.di

import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import org.michaelbel.movies.analytics.di.moviesAnalyticsKoinModule
import org.michaelbel.movies.common.dispatchers.di.dispatchersKoinModule
import org.michaelbel.movies.interactor.MovieBlockingInteractor
import org.michaelbel.movies.interactor.impl.MovieBlockingInteractorImpl
import org.michaelbel.movies.persistence.database.di.moviesDatabaseKoinModule
import org.michaelbel.movies.repository.di.repositoryBlockingKoinModule

internal actual val interactorBlockingKoinModule = module {
    includes(
        dispatchersKoinModule,
        repositoryBlockingKoinModule,
        moviesDatabaseKoinModule,
        moviesAnalyticsKoinModule
    )
    singleOf(::MovieBlockingInteractorImpl) { bind<MovieBlockingInteractor>() }
}