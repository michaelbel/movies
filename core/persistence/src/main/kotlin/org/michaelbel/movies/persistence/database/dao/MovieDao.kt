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

    @Query("SELECT backdropPath FROM movies WHERE id = :movieId")
    fun movieImage(movieId: Int): Flow<String>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllMovies(movies: List<MovieDb>)

    @Query("DELETE FROM movies WHERE movieList = :movieList")
    suspend fun removeAllMovies(movieList: String)

    @Query("SELECT * FROM movies WHERE id = :movieId")
    suspend fun movieById(movieId: Int): MovieDb?

    @Query("SELECT MAX(position) FROM movies WHERE movieList = :movieList")
    suspend fun maxPosition(movieList: String): Int?

    @Query("SELECT COUNT(*) FROM movies WHERE movieList = :movieList")
    suspend fun moviesCount(movieList: String): Int
}