package org.michaelbel.movies.persistence.database.db

import androidx.room3.ConstructedBy
import androidx.room3.Database
import androidx.room3.RoomDatabase
import org.michaelbel.movies.persistence.database.dao.MovieDao
import org.michaelbel.movies.persistence.database.dao.PagingKeyDao
import org.michaelbel.movies.persistence.database.entity.MovieDb
import org.michaelbel.movies.persistence.database.entity.PagingKeyDb

@Database(
    entities = [
        MovieDb::class,
        PagingKeyDb::class
    ],
    version = AppDatabase.DATABASE_VERSION,
    exportSchema = false
)
@ConstructedBy(AppDatabaseConstructor::class)
abstract class AppDatabase: RoomDatabase() {

    abstract fun movieDao(): MovieDao
    abstract fun pagingKeyDao(): PagingKeyDao

    companion object {
        const val DATABASE_NAME = "movies.db"
        const val DATABASE_VERSION = 1
    }
}
