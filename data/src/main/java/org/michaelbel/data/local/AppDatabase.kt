package org.michaelbel.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import org.michaelbel.data.Keyword
import org.michaelbel.data.Review
import org.michaelbel.data.Video
import org.michaelbel.data.local.dao.*

@Database(version = 1, exportSchema = false, entities = [
    Video::class, Review::class, Keyword::class/*, Movie::class*/
])
abstract class AppDatabase: RoomDatabase() {

    companion object {
        private const val DATABASE_NAME = "app_database30.db"

        private lateinit var INSTANCE: AppDatabase

        fun getInstance(context: Context): AppDatabase {
            synchronized(AppDatabase::class) {
                INSTANCE = Room
                        .databaseBuilder(context.applicationContext, AppDatabase::class.java, DATABASE_NAME)
                        .fallbackToDestructiveMigration()
                        .build()

                return INSTANCE
            }
        }
    }

    abstract fun moviesDao(): MoviesDao
    abstract fun trailersDao(): TrailersDao
    abstract fun reviewsDao(): ReviewsDao
    abstract fun keywordsDao(): KeywordsDao
    abstract fun usersDao(): UsersDao
}