package org.michaelbel.movies.persistence.database.di

import android.app.Application
import androidx.room.Room
import kotlinx.coroutines.Dispatchers
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module
import org.michaelbel.movies.persistence.database.db.AppDatabase

actual val databaseKoinModule = module {
    single<AppDatabase> { createRoomDatabase(androidApplication()) }
}

private fun createRoomDatabase(app: Application): AppDatabase {
    val dbFile = app.getDatabasePath(AppDatabase.DATABASE_NAME)
    return Room.databaseBuilder<AppDatabase>(app, dbFile.absolutePath)
        .setQueryCoroutineContext(Dispatchers.IO)
        .fallbackToDestructiveMigration(dropAllTables = true)
        .build()
}