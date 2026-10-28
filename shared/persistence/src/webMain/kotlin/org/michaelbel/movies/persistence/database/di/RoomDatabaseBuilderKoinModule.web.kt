package org.michaelbel.movies.persistence.database.di

import androidx.room3.Room
import androidx.room3.RoomDatabase
import org.koin.dsl.module
import org.michaelbel.movies.persistence.database.createWebWorkerSQLiteDriver
import org.michaelbel.movies.persistence.database.db.AppDatabase

actual val roomDatabaseBuilderModule = module {
    factory<RoomDatabase.Builder<AppDatabase>> {
        Room.databaseBuilder<AppDatabase>(name = AppDatabase.DATABASE_NAME).setDriver(createWebWorkerSQLiteDriver())
    }
}
