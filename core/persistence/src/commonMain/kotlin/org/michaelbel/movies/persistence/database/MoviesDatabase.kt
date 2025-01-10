@file:Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")

package org.michaelbel.movies.persistence.database

import org.michaelbel.movies.persistence.database.dao.AccountDao
import org.michaelbel.movies.persistence.database.dao.ImageDao
import org.michaelbel.movies.persistence.database.dao.MovieDao
import org.michaelbel.movies.persistence.database.dao.PagingKeyDao
import org.michaelbel.movies.persistence.database.dao.SuggestionDao

expect class MoviesDatabase {

    val accountDao: AccountDao

    val imageDao: ImageDao

    val movieDao: MovieDao

    val pagingKeyDao: PagingKeyDao

    val suggestionDao: SuggestionDao

    suspend fun <R> withTransaction(block: suspend () -> R): R
}