package org.michaelbel.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import org.michaelbel.data.local.dao.*
import org.michaelbel.data.local.model.*

@Database(version = 3, exportSchema = false, entities =
[UserLocal::class, MovieLocal::class, ReviewLocal::class, KeywordLocal::class, TrailerLocal::class])
abstract class AppDatabase: RoomDatabase() {

    companion object {
        private const val DATABASE_NAME = "app_database.db"

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

    abstract val usersDao: UsersDao
    abstract val moviesDao: MoviesDao
    abstract val reviewsDao: ReviewsDao
    abstract val keywordsDao: KeywordsDao
    abstract val trailersDao: TrailersDao
}