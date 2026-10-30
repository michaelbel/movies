package org.michaelbel.movies.persistence.database.di

import androidx.room3.Room
import androidx.room3.RoomDatabase
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import org.koin.core.qualifier.named
import org.koin.dsl.module
import org.michaelbel.movies.persistence.database.db.TraktAppDatabase
import java.io.File

actual val traktRoomDatabaseBuilderModule = module {
    factory<RoomDatabase.Builder<TraktAppDatabase>>(named("trakt")) {
        val dbFile = File(System.getProperty("java.io.tmpdir"), TraktAppDatabase.DATABASE_NAME)
        Room.databaseBuilder<TraktAppDatabase>(name = dbFile.absolutePath).setDriver(BundledSQLiteDriver())
    }
}
