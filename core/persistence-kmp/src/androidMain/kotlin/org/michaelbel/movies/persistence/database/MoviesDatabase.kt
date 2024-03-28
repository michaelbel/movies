@file:Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")

package org.michaelbel.movies.persistence.database

import androidx.room.withTransaction
import org.michaelbel.movies.persistence.database.db.AppDatabase

actual class MoviesDatabase internal constructor(
    private val database: AppDatabase
) {

    actual suspend fun <R> withTransaction(block: suspend () -> R): R {
        return database.withTransaction(block)
    }
}