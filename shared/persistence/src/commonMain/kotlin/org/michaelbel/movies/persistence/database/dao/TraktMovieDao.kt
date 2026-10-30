package org.michaelbel.movies.persistence.database.dao

import androidx.paging.PagingSource
import androidx.room3.Dao
import androidx.room3.DaoReturnTypeConverters
import androidx.room3.Query
import androidx.room3.Upsert
import androidx.room3.paging.PagingSourceDaoReturnTypeConverter
import org.michaelbel.movies.persistence.database.entity.TraktMovieDb
import org.michaelbel.movies.persistence.database.typealiases.PagingKey

/**
 * The Data Access Object for the [TraktMovieDb] class.
 */
@Dao
@DaoReturnTypeConverters(PagingSourceDaoReturnTypeConverter::class)
interface TraktMovieDao {

    @Query("SELECT * FROM trakt_movies WHERE traktList = :traktList ORDER BY position ASC")
    fun pagingSource(traktList: PagingKey): PagingSource<Int, TraktMovieDb>

    @Upsert
    suspend fun upsert(movies: List<TraktMovieDb>)

    @Query("DELETE FROM trakt_movies WHERE traktList = :traktList")
    suspend fun removeMovies(traktList: PagingKey)

    @Query("SELECT COALESCE(MAX(position), 0) FROM trakt_movies WHERE traktList = :traktList")
    suspend fun maxPosition(traktList: PagingKey): Int

    @Query("SELECT (SELECT COUNT(*) FROM trakt_movies WHERE traktList = :traktList) == 0")
    suspend fun isEmpty(traktList: PagingKey): Boolean
}
