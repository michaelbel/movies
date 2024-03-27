@file:Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")

package org.michaelbel.movies.persistence.database

actual class MoviesDatabase internal constructor() {

    suspend fun <R> withTransaction(block: suspend () -> R): R {
        return block.invoke()
    }
}