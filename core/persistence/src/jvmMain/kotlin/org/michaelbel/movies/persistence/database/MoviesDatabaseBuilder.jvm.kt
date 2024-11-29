package org.michaelbel.movies.persistence.database

import androidx.room.RoomDatabase
import kotlinx.coroutines.CoroutineDispatcher
import org.michaelbel.movies.persistence.database.db.AppDatabase

actual fun createMoviesDatabase(
    databaseBuilder: RoomDatabase.Builder<AppDatabase>,
    dispatcher: CoroutineDispatcher
): MoviesDatabase {
    return MoviesDatabase(
        databaseBuilder
            .setQueryCoroutineContext(dispatcher)
            .fallbackToDestructiveMigration(dropAllTables = false)
            .build()
    )
}