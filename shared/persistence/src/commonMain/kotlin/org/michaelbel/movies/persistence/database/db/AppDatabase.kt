package org.michaelbel.movies.persistence.database.db

import androidx.room3.ConstructedBy
import androidx.room3.Database
import androidx.room3.RoomDatabase
import org.michaelbel.movies.persistence.database.dao.AccountDao
import org.michaelbel.movies.persistence.database.dao.ImageDao
import org.michaelbel.movies.persistence.database.dao.MovieDao
import org.michaelbel.movies.persistence.database.dao.PagingKeyDao
import org.michaelbel.movies.persistence.database.dao.SuggestionDao
import org.michaelbel.movies.persistence.database.entity.AccountDb
import org.michaelbel.movies.persistence.database.entity.ImageDb
import org.michaelbel.movies.persistence.database.entity.MovieDb
import org.michaelbel.movies.persistence.database.entity.PagingKeyDb
import org.michaelbel.movies.persistence.database.entity.SuggestionDb

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
@ConstructedBy(AppDatabaseConstructor::class)
abstract class AppDatabase: RoomDatabase() {

    abstract fun movieDao(): MovieDao
    abstract fun imageDao(): ImageDao
    abstract fun accountDao(): AccountDao
    abstract fun pagingKeyDao(): PagingKeyDao
    abstract fun suggestionDao(): SuggestionDao

    companion object {
        const val DATABASE_NAME = "movies.db"
        const val DATABASE_VERSION = 31
    }
}
