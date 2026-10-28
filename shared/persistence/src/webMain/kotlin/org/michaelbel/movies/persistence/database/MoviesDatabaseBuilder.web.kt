package org.michaelbel.movies.persistence.database

import androidx.room3.RoomDatabase
import kotlinx.coroutines.CoroutineDispatcher
import org.michaelbel.movies.persistence.database.db.AppDatabase

/**
 * [dispatcher] is ignored on web: queries run through [androidx.sqlite.driver.web.WebWorkerSQLiteDriver],
 * which executes SQLite in a dedicated Web Worker.
 */
actual fun createMoviesDatabase(
    databaseBuilder: RoomDatabase.Builder<AppDatabase>,
    dispatcher: CoroutineDispatcher
): MoviesDatabase {
    return MoviesDatabase(
        databaseBuilder
            .fallbackToDestructiveMigration(dropAllTables = false)
            .build()
    )
}
