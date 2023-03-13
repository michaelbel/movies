package org.michaelbel.movies.domain.data.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import org.michaelbel.movies.domain.data.AppDatabase
import org.michaelbel.movies.domain.data.dao.AccountDao
import org.michaelbel.movies.domain.data.dao.MovieDao
import org.michaelbel.movies.domain.data.dao.PagingKeyDao
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(
        @ApplicationContext context: Context
    ): AppDatabase = AppDatabase.getInstance(context)

    @Provides
    fun provideMovieDao(appDatabase: AppDatabase): MovieDao = appDatabase.movieDao()

    @Provides
    fun provideAccountDao(appDatabase: AppDatabase): AccountDao = appDatabase.accountDao()

    @Provides
    fun providePagingKeyDao(appDatabase: AppDatabase): PagingKeyDao = appDatabase.pagingKeyDao()
}