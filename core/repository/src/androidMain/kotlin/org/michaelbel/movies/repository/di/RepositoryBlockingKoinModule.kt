package org.michaelbel.movies.repository.di

import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import org.michaelbel.movies.persistence.database.di.persistenceBlockingKoinModule
import org.michaelbel.movies.repository.MovieBlockingRepository
import org.michaelbel.movies.repository.impl.MovieBlockingRepositoryImpl

actual val repositoryBlockingKoinModule = module {
    includes(
        persistenceBlockingKoinModule
    )
    singleOf(::MovieBlockingRepositoryImpl) { bind<MovieBlockingRepository>() }
}