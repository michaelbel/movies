package org.michaelbel.movies.persistence.database.di

import androidx.room3.Room
import org.koin.dsl.module
import org.michaelbel.movies.persistence.database.MoviePersistence
import org.michaelbel.movies.persistence.database.MoviesDatabase
import org.michaelbel.movies.persistence.database.PagingKeyPersistence
import org.michaelbel.movies.persistence.database.createWebWorkerSQLiteDriver
import org.michaelbel.movies.persistence.database.db.AppDatabase

val moviesDatabaseKoinModule = module {
    single<MoviesDatabase> {
        MoviesDatabase(
            Room.databaseBuilder<AppDatabase>(name = AppDatabase.DATABASE_NAME)
                .setDriver(createWebWorkerSQLiteDriver())
                .fallbackToDestructiveMigration(dropAllTables = false)
                .build()
        )
    }
    single { MoviePersistence(get<MoviesDatabase>()) }
    single { PagingKeyPersistence(get<MoviesDatabase>()) }
}
