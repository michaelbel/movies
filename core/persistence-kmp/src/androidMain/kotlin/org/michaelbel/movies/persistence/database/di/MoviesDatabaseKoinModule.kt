package org.michaelbel.movies.persistence.database.di

import org.koin.dsl.module
import org.michaelbel.movies.persistence.database.MoviesDatabase

val moviesDatabaseKoinModule = module {
    includes(
        databaseKoinModule
    )
    single { MoviesDatabase(get()) }
}