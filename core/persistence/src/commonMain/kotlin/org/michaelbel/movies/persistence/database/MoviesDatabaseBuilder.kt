package org.michaelbel.movies.persistence.database

import androidx.room.RoomDatabase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import org.michaelbel.movies.persistence.database.db.AppDatabase

expect fun createMoviesDatabase(
    databaseBuilder: RoomDatabase.Builder<AppDatabase>,
    dispatcher: CoroutineDispatcher = Dispatchers.IO
): MoviesDatabase