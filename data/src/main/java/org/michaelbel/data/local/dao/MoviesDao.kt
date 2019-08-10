package org.michaelbel.data.local.dao

import androidx.room.*
import org.michaelbel.data.local.model.MovieLocal

@Dao
abstract class MoviesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(movie: MovieLocal)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(vararg movies: MovieLocal)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insert(movies: ArrayList<MovieLocal>)

    @Update
    abstract fun update(movie: MovieLocal)

    @Delete
    abstract fun delete(movie: MovieLocal)

    @Query("select * from movies")
    abstract fun getAll(): List<MovieLocal>

    @Query("select * from movies where movie_id = :id")
    abstract fun getAll(id: Int): List<MovieLocal>

    @Query("delete from movies")
    abstract fun deleteAll()
}