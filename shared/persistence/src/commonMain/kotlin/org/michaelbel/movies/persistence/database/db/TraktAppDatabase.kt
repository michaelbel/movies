package org.michaelbel.movies.persistence.database.db

import androidx.room3.ConstructedBy
import androidx.room3.Database
import androidx.room3.RoomDatabase
import org.michaelbel.movies.persistence.database.dao.TraktMovieDao
import org.michaelbel.movies.persistence.database.dao.TraktPagingKeyDao
import org.michaelbel.movies.persistence.database.entity.TraktMovieDb
import org.michaelbel.movies.persistence.database.entity.TraktPagingKeyDb

@Database(
    entities = [
        TraktMovieDb::class,
        TraktPagingKeyDb::class
    ],
    version = TraktAppDatabase.DATABASE_VERSION,
    exportSchema = false
)
@ConstructedBy(TraktAppDatabaseConstructor::class)
abstract class TraktAppDatabase: RoomDatabase() {

    abstract fun traktMovieDao(): TraktMovieDao
    abstract fun traktPagingKeyDao(): TraktPagingKeyDao

    companion object {
        const val DATABASE_NAME = "trakt_movies.db"
        const val DATABASE_VERSION = 2
    }
}
