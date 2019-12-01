package org.michaelbel.moviemade.presentation.di.module

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import org.michaelbel.data.local.AppDatabase
import org.michaelbel.moviemade.core.local.SharedPrefs.NAME
import javax.inject.Singleton

@Module
class AppModule(context: Context) {

    private val appContext: Context = context.applicationContext

    @Provides
    @Singleton
    fun provideContext(): Context = appContext

    @Provides
    @Singleton
    fun sharedPreferences(): SharedPreferences = appContext.getSharedPreferences(NAME, MODE_PRIVATE)

    @Provides
    @Singleton
    fun appDatabase() = AppDatabase.getInstance(appContext)

    //region Dao

    @Provides
    fun trailersDao(db: AppDatabase) = db.trailersDao()

    @Provides
    fun reviewsDao(db: AppDatabase) = db.reviewsDao()

    @Provides
    fun keywordsDao(db: AppDatabase) = db.keywordsDao()

    @Provides
    fun moviesDao(db: AppDatabase) = db.moviesDao()

    @Provides
    fun usersDao(db: AppDatabase) = db.usersDao()

    //endregion
}