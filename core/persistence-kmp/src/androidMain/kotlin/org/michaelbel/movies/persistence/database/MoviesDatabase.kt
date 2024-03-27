package org.michaelbel.movies.persistence.database

import androidx.room.withTransaction
import org.michaelbel.movies.persistence.database.db.AppDatabase

class MoviesDatabase internal constructor(
    private val database: AppDatabase
) {

    suspend fun <R> withTransaction(block: suspend () -> R): R {
        return database.withTransaction(block)
    }
}