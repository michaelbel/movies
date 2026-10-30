@file:Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")

package org.michaelbel.movies.persistence.database

import org.michaelbel.movies.persistence.database.dao.TraktMovieDao
import org.michaelbel.movies.persistence.database.dao.TraktPagingKeyDao

expect class TraktDatabase {

    val traktMovieDao: TraktMovieDao

    val traktPagingKeyDao: TraktPagingKeyDao

    suspend fun <R> withTransaction(block: suspend () -> R): R
}
