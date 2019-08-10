package org.michaelbel.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import org.michaelbel.data.local.dao.*
import org.michaelbel.data.local.model.*

@Database(version = 1, exportSchema = false, entities = [UserLocal::class, MovieLocal::class, ReviewLocal::class, KeywordLocal::class, TrailerLocal::class])
abstract class AppDatabase: RoomDatabase() {

    companion object {
        private const val DATABASE_NAME = "app_database33.db"

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

    abstract fun usersDao(): UsersDao
    abstract fun moviesDao(): MoviesDao
    abstract fun reviewsDao(): ReviewsDao
    abstract fun keywordsDao(): KeywordsDao
    abstract fun trailersDao(): TrailersDao
}