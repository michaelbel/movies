package org.michaelbel.movies.persistence.database.di

import androidx.room.RoomDatabase
import org.koin.dsl.module
import org.michaelbel.movies.persistence.database.MoviesDatabase
import org.michaelbel.movies.persistence.database.createMoviesDatabase
import org.michaelbel.movies.persistence.database.db.AppDatabase

val moviesDatabaseKoinModule = module {
    includes(
        roomDatabaseBuilderModule
    )
    single<MoviesDatabase> { createMoviesDatabase(get<RoomDatabase.Builder<AppDatabase>>()) }
}