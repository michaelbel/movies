package org.michaelbel.movies.persistence.database

import androidx.room3.RoomDatabase
import kotlinx.coroutines.CoroutineDispatcher
import org.michaelbel.movies.persistence.database.db.TraktAppDatabase

/**
 * [dispatcher] is ignored on web: queries run through [androidx.sqlite.driver.web.WebWorkerSQLiteDriver],
 * which executes SQLite in a dedicated Web Worker.
 */
actual fun createTraktDatabase(
    databaseBuilder: RoomDatabase.Builder<TraktAppDatabase>,
    dispatcher: CoroutineDispatcher
): TraktDatabase {
    return TraktDatabase(
        databaseBuilder
            .fallbackToDestructiveMigration(dropAllTables = false)
            .build()
    )
}
