package org.michaelbel.movies.domain.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import org.michaelbel.movies.domain.data.entity.MovieDb

/**
 * The Data Access Object for the [MovieDb] class.
 */
@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(movies: List<MovieDb>)

    @Query("SELECT * FROM movies WHERE id = :movieId")
    suspend fun movieById(movieId: Int): MovieDb?
}