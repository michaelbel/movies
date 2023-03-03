package org.michaelbel.movies.domain.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import org.michaelbel.movies.domain.data.converter.CalendarConverter
import org.michaelbel.movies.domain.data.dao.MovieDao
import org.michaelbel.movies.domain.data.dao.PagingKeyDao
import org.michaelbel.movies.domain.data.entity.MovieDb
import org.michaelbel.movies.domain.data.entity.PagingKeyDb

/**
 * The Room database for this app
 */
@Database(
    entities = [
        MovieDb::class,
        PagingKeyDb::class
    ],
    version = AppDatabase.DATABASE_VERSION,
    exportSchema = false
)
@TypeConverters(CalendarConverter::class)
abstract class AppDatabase: RoomDatabase() {

    abstract fun movieDao(): MovieDao
    abstract fun pagingKeyDao(): PagingKeyDao

    companion object {
        private const val DATABASE_NAME = "movies-db"
        const val DATABASE_VERSION = 13

        @Volatile
        private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME)
                .fallbackToDestructiveMigration()
                .build()
        }
    }
}