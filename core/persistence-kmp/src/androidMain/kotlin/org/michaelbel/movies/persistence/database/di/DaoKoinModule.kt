package org.michaelbel.movies.persistence.database.di

import org.koin.dsl.module
import org.michaelbel.movies.persistence.database.db.AppDatabase

actual val daoKoinModule = module {
    includes(
        databaseKoinModule
    )
    single { get<AppDatabase>().movieDao() }
    single { get<AppDatabase>().imageDao() }
    single { get<AppDatabase>().accountDao() }
    single { get<AppDatabase>().pagingKeyDao() }
    single { get<AppDatabase>().suggestionDao() }
}