package org.michaelbel.movies.persistence.database

import androidx.room3.withWriteTransaction
import org.michaelbel.movies.persistence.database.dao.MovieDao
import org.michaelbel.movies.persistence.database.dao.PagingKeyDao
import org.michaelbel.movies.persistence.database.db.AppDatabase

class MoviesDatabase(
    private val database: AppDatabase
) {

    val movieDao: MovieDao
        get() = database.movieDao()

    val pagingKeyDao: PagingKeyDao
        get() = database.pagingKeyDao()

    suspend fun <R> withTransaction(block: suspend () -> R): R {
        return database.withWriteTransaction { block() }
    }
}
