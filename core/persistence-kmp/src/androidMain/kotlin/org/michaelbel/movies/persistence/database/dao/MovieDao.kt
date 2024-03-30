package org.michaelbel.movies.persistence.database.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.RewriteQueriesToDropUnusedColumns
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow
import org.michaelbel.movies.persistence.database.entity.MovieDb
import org.michaelbel.movies.persistence.database.entity.MoviePojo
import org.michaelbel.movies.persistence.database.entity.mini.MovieDbMini

/**
 * The Data Access Object for the [MovieDb] class.
 */
@Dao
internal interface MovieDao {

    @Transaction
    @Query("SELECT * FROM movies WHERE movieList = :movieList ORDER BY position ASC")
    fun pagingSource(movieList: String): PagingSource<Int, MoviePojo>

    @Query("SELECT * FROM movies WHERE movieList = :movieList ORDER BY position DESC LIMIT :limit")
    fun moviesFlow(movieList: String, limit: Int): Flow<List<MoviePojo>>

    @Query("SELECT * FROM movies WHERE movieList = :movieList ORDER BY position ASC LIMIT :limit")
    suspend fun movies(movieList: String, limit: Int): List<MoviePojo>

    @RewriteQueriesToDropUnusedColumns
    @Query("SELECT * FROM movies WHERE movieList = :movieList ORDER BY position ASC LIMIT :limit")
    suspend fun moviesMini(movieList: String, limit: Int): List<MovieDbMini>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovies(movies: List<MovieDb>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(movie: MovieDb)

    @Transaction
    @Query("DELETE FROM movies WHERE movieList = :movieList")
    suspend fun removeMovies(movieList: String)

    @Query("DELETE FROM movies WHERE movieList = :movieList AND movieId = :movieId")
    suspend fun removeMovie(movieList: String, movieId: Int)

    @Query("SELECT * FROM movies WHERE movieList = :pagingKey AND movieId = :movieId")
    suspend fun movieById(pagingKey: String, movieId: Int): MoviePojo?

    @Transaction
    @Query("SELECT MAX(position) FROM movies WHERE movieList = :movieList")
    suspend fun maxPosition(movieList: String): Int?

    @Query("SELECT (SELECT COUNT(*) FROM movies WHERE movieList = :movieList) == 0")
    suspend fun isEmpty(movieList: String): Boolean

    @Query("UPDATE movies SET containerColor = :containerColor, onContainerColor = :onContainerColor WHERE movieId = :movieId")
    suspend fun updateMovieColors(movieId: Int, containerColor: Int, onContainerColor: Int)
}