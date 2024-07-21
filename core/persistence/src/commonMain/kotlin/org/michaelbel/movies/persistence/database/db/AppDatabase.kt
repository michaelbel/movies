package org.michaelbel.movies.persistence.database.db

import androidx.room.Database
import androidx.room.RoomDatabase
import org.michaelbel.movies.persistence.database.dao.AccountDao
import org.michaelbel.movies.persistence.database.dao.ImageDao
import org.michaelbel.movies.persistence.database.dao.MovieBlockingDao
import org.michaelbel.movies.persistence.database.dao.MovieDao
import org.michaelbel.movies.persistence.database.dao.PagingKeyDao
import org.michaelbel.movies.persistence.database.dao.SuggestionDao
import org.michaelbel.movies.persistence.database.entity.AccountDb
import org.michaelbel.movies.persistence.database.entity.ImageDb
import org.michaelbel.movies.persistence.database.entity.MovieDb
import org.michaelbel.movies.persistence.database.entity.PagingKeyDb
import org.michaelbel.movies.persistence.database.entity.SuggestionDb

/**
 * The Room database for this app.
 */
@Database(
    entities = [
        MovieDb::class,
        ImageDb::class,
        AccountDb::class,
        PagingKeyDb::class,
        SuggestionDb::class
    ],
    version = AppDatabase.DATABASE_VERSION,
    exportSchema = false
)
abstract class AppDatabase: RoomDatabase() {

    internal abstract fun movieDao(): MovieDao
    internal abstract fun movieBlockingDao(): MovieBlockingDao
    internal abstract fun imageDao(): ImageDao
    internal abstract fun accountDao(): AccountDao
    internal abstract fun pagingKeyDao(): PagingKeyDao
    internal abstract fun suggestionDao(): SuggestionDao

    companion object {
        const val DATABASE_NAME = "movies.db"
        const val DATABASE_VERSION = 28
    }
}