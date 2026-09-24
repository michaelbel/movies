package org.michaelbel.movies.persistence.database.dao

import androidx.paging.PagingSource
import androidx.room3.Dao
import androidx.room3.Query
import androidx.room3.RewriteQueriesToDropUnusedColumns
import androidx.room3.Transaction
import androidx.room3.Upsert
import androidx.room3.DaoReturnTypeConverters
import androidx.room3.paging.PagingSourceDaoReturnTypeConverter
import kotlinx.coroutines.flow.Flow
import org.michaelbel.movies.persistence.database.entity.MovieDb
import org.michaelbel.movies.persistence.database.entity.mini.MovieDbMini
import org.michaelbel.movies.persistence.database.entity.pojo.MoviePojo
import org.michaelbel.movies.persistence.database.typealiases.Limit
import org.michaelbel.movies.persistence.database.typealiases.MovieId
import org.michaelbel.movies.persistence.database.typealiases.PagingKey

/**
 * The Data Access Object for the [MovieDb] class.
 */
@Dao
@DaoReturnTypeConverters(PagingSourceDaoReturnTypeConverter::class)
interface MovieDao {

    @Transaction
    @Query("SELECT * FROM movies WHERE movieList = :pagingKey ORDER BY position ASC")
    fun pagingSource(pagingKey: PagingKey): PagingSource<Int, MoviePojo>

    @Query("SELECT * FROM movies WHERE movieList = :pagingKey AND movieId = :movieId")
    fun movieFlow(pagingKey: PagingKey, movieId: MovieId): Flow<MoviePojo?>

    @Query("SELECT * FROM movies WHERE movieList = :pagingKey ORDER BY position DESC LIMIT :limit")
    fun moviesFlow(pagingKey: PagingKey, limit: Limit): Flow<List<MoviePojo>>

    @Query("SELECT * FROM movies WHERE movieList = :pagingKey ORDER BY position ASC LIMIT :limit")
    suspend fun movies(pagingKey: PagingKey, limit: Limit): List<MoviePojo>

    @RewriteQueriesToDropUnusedColumns
    @Query("SELECT * FROM movies WHERE movieList = :pagingKey ORDER BY position ASC LIMIT :limit")
    suspend fun moviesMini(pagingKey: PagingKey, limit: Limit): List<MovieDbMini>

    @Upsert
    suspend fun upsert(movies: List<MovieDb>)

    @Upsert
    suspend fun upsert(movie: MovieDb)

    @Query("DELETE FROM movies WHERE movieList = :pagingKey")
    suspend fun removeMovies(pagingKey: PagingKey)

    @Query("DELETE FROM movies WHERE movieList = :pagingKey AND movieId = :movieId")
    suspend fun removeMovie(pagingKey: PagingKey, movieId: MovieId)

    @Query("SELECT * FROM movies WHERE movieList = :pagingKey AND movieId = :movieId")
    suspend fun movieById(pagingKey: PagingKey, movieId: MovieId): MoviePojo?

    @Query("SELECT * FROM movies WHERE movieId = :movieId ORDER BY dateAdded DESC LIMIT 1")
    suspend fun movieById(movieId: MovieId): MoviePojo?

    @Query("SELECT COALESCE(MAX(position), 0) FROM movies WHERE movieList = :pagingKey")
    suspend fun maxPosition(pagingKey: PagingKey): Int

    @Query("SELECT (SELECT COUNT(*) FROM movies WHERE movieList = :pagingKey) == 0")
    suspend fun isEmpty(pagingKey: PagingKey): Boolean
}
