package org.michaelbel.movies.persistence.database.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import org.michaelbel.movies.persistence.database.entity.MovieDb

/**
 * The Data Access Object for the [MovieDb] class.
 */
@Dao
interface MovieDao {

    @Query("SELECT * FROM movies WHERE movieList = :movieList ORDER BY position ASC")
    fun pagingSource(movieList: String): PagingSource<Int, MovieDb>

    @Query("SELECT * FROM movies WHERE movieList = :movieList ORDER BY position DESC LIMIT :limit")
    fun moviesFlow(movieList: String, limit: Int): Flow<List<MovieDb>>

    @Query("SELECT * FROM movies WHERE movieList = :movieList ORDER BY position ASC LIMIT :limit")
    suspend fun movies(movieList: String, limit: Int): List<MovieDb>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovies(movies: List<MovieDb>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(movie: MovieDb)

    @Query("DELETE FROM movies WHERE movieList = :movieList")
    suspend fun removeMovies(movieList: String)

    @Query("DELETE FROM movies WHERE movieList = :movieList AND id = :movieId")
    suspend fun removeMovie(movieList: String, movieId: Int)

    @Query("SELECT * FROM movies WHERE id = :movieId")
    suspend fun movieById(movieId: Int): MovieDb?

    @Query("SELECT * FROM movies WHERE movieList = :pagingKey AND id = :movieId")
    suspend fun movieById(pagingKey: String, movieId: Int): MovieDb?

    @Query("SELECT MAX(position) FROM movies WHERE movieList = :movieList")
    suspend fun maxPosition(movieList: String): Int?

    @Query("SELECT COUNT(*) FROM movies WHERE movieList = :movieList")
    suspend fun moviesCount(movieList: String): Int
}