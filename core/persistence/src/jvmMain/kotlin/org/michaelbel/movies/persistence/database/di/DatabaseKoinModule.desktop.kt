package org.michaelbel.movies.persistence.database.di

import androidx.room.Room
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import java.io.File
import kotlinx.coroutines.Dispatchers
import org.koin.dsl.module
import org.michaelbel.movies.persistence.database.db.AppDatabase

actual val databaseKoinModule = module {
    single<AppDatabase> { createRoomDatabase() }
}

private fun createRoomDatabase(): AppDatabase {
    val dbFile = File(System.getProperty("java.io.tmpdir"), AppDatabase.DATABASE_NAME)
    return Room
        .databaseBuilder<AppDatabase>(name = dbFile.absolutePath)
        .fallbackToDestructiveMigrationOnDowngrade(dropAllTables = true)
        .setDriver(BundledSQLiteDriver())
        .setQueryCoroutineContext(Dispatchers.IO)
        .build()
}