package org.michaelbel.movies.persistence.database.di

import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import org.koin.dsl.module
import org.michaelbel.movies.persistence.database.db.AppDatabase
import java.io.File

actual val roomDatabaseBuilderModule = module {
    factory<RoomDatabase.Builder<AppDatabase>> {
        val dbFile = File(System.getProperty("java.io.tmpdir"), AppDatabase.DATABASE_NAME)
        Room.databaseBuilder<AppDatabase>(name = dbFile.absolutePath).setDriver(BundledSQLiteDriver())
    }
}