package org.michaelbel.movies.persistence.database

import androidx.room3.RoomDatabase
import kotlinx.coroutines.CoroutineDispatcher
import org.michaelbel.movies.persistence.database.db.AppDatabase

expect fun createMoviesDatabase(
    databaseBuilder: RoomDatabase.Builder<AppDatabase>,
    dispatcher: CoroutineDispatcher = databaseDispatcher
): MoviesDatabase
