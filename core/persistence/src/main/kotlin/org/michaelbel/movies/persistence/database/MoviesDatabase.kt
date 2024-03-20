package org.michaelbel.movies.persistence.database

import androidx.room.withTransaction
import org.michaelbel.movies.persistence.database.db.AppDatabase
import javax.inject.Inject

class MoviesDatabase @Inject internal constructor(
    private val database: AppDatabase
) {

    suspend fun <R> withTransaction(block: suspend () -> R): R {
        return database.withTransaction(block)
    }
}