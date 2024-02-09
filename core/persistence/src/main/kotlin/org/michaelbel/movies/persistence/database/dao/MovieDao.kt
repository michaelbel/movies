package org.michaelbel.movies.persistence.database.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow
import org.michaelbel.movies.persistence.database.entity.MovieDb

/**
 * The Data Access Object for the [MovieDb] class.
 */
@Dao
interface MovieDao {

    @Transaction
    @Query("SELECT * FROM movies WHERE movieList = :movieList ORDER BY position ASC")
    fun pagingSource(movieList: String): PagingSource<Int, MovieDb>

    @Query("SELECT * FROM movies WHERE movieList = :movieList ORDER BY position DESC LIMIT :limit")
    fun moviesFlow(movieList: String, limit: Int): Flow<List<MovieDb>>

    @Query("SELECT * FROM movies WHERE movieList = :movieList ORDER BY position ASC LIMIT :limit")
    suspend fun movies(movieList: String, limit: Int): List<MovieDb>

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovies(movies: List<MovieDb>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(movie: MovieDb)

    @Transaction
    @Query("DELETE FROM movies WHERE movieList = :movieList")
    suspend fun removeMovies(movieList: String)

    @Query("DELETE FROM movies WHERE movieList = :movieList AND id = :movieId")
    suspend fun removeMovie(movieList: String, movieId: Int)

    @Query("SELECT * FROM movies WHERE id = :movieId")
    suspend fun movieById(movieId: Int): MovieDb?

    @Query("SELECT * FROM movies WHERE movieList = :pagingKey AND id = :movieId")
    suspend fun movieById(pagingKey: String, movieId: Int): MovieDb?

    @Transaction
    @Query("SELECT MAX(position) FROM movies WHERE movieList = :movieList")
    suspend fun maxPosition(movieList: String): Int?

    @Query("SELECT (SELECT COUNT(*) FROM movies WHERE movieList = :movieList) == 0")
    suspend fun isEmpty(movieList: String): Boolean

    @Query("UPDATE movies SET containerColor = :containerColor, onContainerColor = :onContainerColor WHERE id = :movieId")
    suspend fun updateMovieColors(movieId: Int, containerColor: Int, onContainerColor: Int)
}