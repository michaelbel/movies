package org.michaelbel.movies.persistence.database.di

import org.koin.dsl.module
import org.michaelbel.movies.persistence.database.MovieBlockingPersistence

actual val persistenceBlockingKoinModule = module {
    includes(
        daoKoinModule
    )
    single { MovieBlockingPersistence(get()) }
}