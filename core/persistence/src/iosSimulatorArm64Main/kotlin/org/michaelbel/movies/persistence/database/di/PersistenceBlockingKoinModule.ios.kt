package org.michaelbel.movies.persistence.database.di

import org.koin.dsl.module
import org.michaelbel.movies.persistence.database.MovieBlockingPersistence
import org.michaelbel.movies.persistence.database.MoviesDatabase

actual val persistenceBlockingKoinModule = module {
    includes(
        moviesDatabaseKoinModule
    )
    single { MovieBlockingPersistence(get<MoviesDatabase>()) }
}