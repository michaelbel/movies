package org.michaelbel.movies.persistence.database.di

import androidx.room3.Room
import androidx.room3.RoomDatabase
import org.koin.core.qualifier.named
import org.koin.dsl.module
import org.michaelbel.movies.persistence.database.createWebWorkerSQLiteDriver
import org.michaelbel.movies.persistence.database.db.TraktAppDatabase

actual val traktRoomDatabaseBuilderModule = module {
    factory<RoomDatabase.Builder<TraktAppDatabase>>(named("trakt")) {
        Room.databaseBuilder<TraktAppDatabase>(name = TraktAppDatabase.DATABASE_NAME).setDriver(createWebWorkerSQLiteDriver())
    }
}
