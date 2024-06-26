@file:Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")

package org.michaelbel.movies.persistence.database

expect class MoviesDatabase {

    suspend fun <R> withTransaction(block: suspend () -> R): R
}