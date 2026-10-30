@file:Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")

package org.michaelbel.movies.persistence.database

import org.michaelbel.movies.persistence.database.dao.TraktMovieDao
import org.michaelbel.movies.persistence.database.dao.TraktPagingKeyDao
import org.michaelbel.movies.persistence.database.db.TraktAppDatabase

actual class TraktDatabase(
    private val database: TraktAppDatabase
) {

    actual val traktMovieDao: TraktMovieDao
        get() = database.traktMovieDao()

    actual val traktPagingKeyDao: TraktPagingKeyDao
        get() = database.traktPagingKeyDao()

    actual suspend fun <R> withTransaction(block: suspend () -> R): R {
        return block.invoke()
    }
}
