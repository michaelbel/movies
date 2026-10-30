package org.michaelbel.movies.persistence.database

import androidx.room3.RoomDatabase
import kotlinx.coroutines.CoroutineDispatcher
import org.michaelbel.movies.persistence.database.db.TraktAppDatabase

actual fun createTraktDatabase(
    databaseBuilder: RoomDatabase.Builder<TraktAppDatabase>,
    dispatcher: CoroutineDispatcher
): TraktDatabase {
    return TraktDatabase(
        databaseBuilder
            .setQueryCoroutineContext(dispatcher)
            .fallbackToDestructiveMigration(dropAllTables = false)
            .build()
    )
}
