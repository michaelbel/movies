@file:Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")

package org.michaelbel.movies.persistence.database

import androidx.room.withTransaction
import org.michaelbel.movies.persistence.database.dao.AccountDao
import org.michaelbel.movies.persistence.database.dao.ImageDao
import org.michaelbel.movies.persistence.database.dao.MovieBlockingDao
import org.michaelbel.movies.persistence.database.dao.MovieDao
import org.michaelbel.movies.persistence.database.dao.PagingKeyDao
import org.michaelbel.movies.persistence.database.dao.SuggestionDao
import org.michaelbel.movies.persistence.database.db.AppDatabase

actual class MoviesDatabase internal constructor(
    private val database: AppDatabase
) {

    actual val accountDao: AccountDao
        get() = database.accountDao()

    actual val imageDao: ImageDao
        get() = database.imageDao()

    actual val movieBlockingDao: MovieBlockingDao
        get() = database.movieBlockingDao()

    actual val movieDao: MovieDao
        get() = database.movieDao()

    actual val pagingKeyDao: PagingKeyDao
        get() = database.pagingKeyDao()

    actual val suggestionDao: SuggestionDao
        get() = database.suggestionDao()

    actual suspend fun <R> withTransaction(block: suspend () -> R): R {
        return database.withTransaction(block)
    }
}