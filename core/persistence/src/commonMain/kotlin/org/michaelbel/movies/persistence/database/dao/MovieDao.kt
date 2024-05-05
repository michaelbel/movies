package org.michaelbel.movies.persistence.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.RewriteQueriesToDropUnusedColumns
import androidx.room.Transaction
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
internal interface MovieDao {

    /*@Transaction
    @Query("SELECT * FROM movies WHERE movieList = :pagingKey ORDER BY position ASC")
    fun pagingSource(pagingKey: PagingKey): PagingSource<Int, MoviePojo>*/

    @Query("SELECT * FROM movies WHERE movieList = :pagingKey ORDER BY position DESC LIMIT :limit")
    fun moviesFlow(pagingKey: PagingKey, limit: Limit): Flow<List<MoviePojo>>

    @Query("SELECT * FROM movies WHERE movieList = :pagingKey ORDER BY position ASC LIMIT :limit")
    suspend fun movies(pagingKey: PagingKey, limit: Limit): List<MoviePojo>

    @RewriteQueriesToDropUnusedColumns
    @Query("SELECT * FROM movies WHERE movieList = :pagingKey ORDER BY position ASC LIMIT :limit")
    suspend fun moviesMini(pagingKey: PagingKey, limit: Limit): List<MovieDbMini>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovies(movies: List<MovieDb>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(movie: MovieDb)

    @Transaction
    @Query("DELETE FROM movies WHERE movieList = :pagingKey")
    suspend fun removeMovies(pagingKey: PagingKey)

    @Query("DELETE FROM movies WHERE movieList = :pagingKey AND movieId = :movieId")
    suspend fun removeMovie(pagingKey: PagingKey, movieId: MovieId)

    @Query("SELECT * FROM movies WHERE movieList = :pagingKey AND movieId = :movieId")
    suspend fun movieById(pagingKey: PagingKey, movieId: MovieId): MoviePojo?

    @Transaction
    @Query("SELECT MAX(position) FROM movies WHERE movieList = :pagingKey")
    suspend fun maxPosition(pagingKey: PagingKey): Int?

    @Query("SELECT (SELECT COUNT(*) FROM movies WHERE movieList = :pagingKey) == 0")
    suspend fun isEmpty(pagingKey: PagingKey): Boolean

    @Query("UPDATE movies SET containerColor = :containerColor, onContainerColor = :onContainerColor WHERE movieId = :movieId")
    suspend fun updateMovieColors(movieId: MovieId, containerColor: Int, onContainerColor: Int)
}