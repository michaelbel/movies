package org.michaelbel.moviemade.presentation.di.module

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import org.michaelbel.data.local.AppDatabase
import org.michaelbel.moviemade.core.local.SharedPrefs.NAME
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun sharedPreferences(@ApplicationContext context: Context): SharedPreferences =
        context.getSharedPreferences(NAME, MODE_PRIVATE)

    @Provides
    @Singleton
    fun appDatabase(@ApplicationContext context: Context) = AppDatabase.getInstance(context)

    //region Dao

    @Provides
    fun trailersDao(db: AppDatabase) = db.trailersDao

    @Provides
    fun reviewsDao(db: AppDatabase) = db.reviewsDao

    @Provides
    fun keywordsDao(db: AppDatabase) = db.keywordsDao

    @Provides
    fun moviesDao(db: AppDatabase) = db.moviesDao

    @Provides
    fun usersDao(db: AppDatabase) = db.usersDao

    //endregion
}