package org.michaelbel.data.local.dao

import androidx.room.Dao
import org.michaelbel.data.Movie
import org.michaelbel.data.local.BaseDao

@Dao
abstract class MoviesDao: BaseDao<Movie>() {

    /*@Query("select * from movies where id = :id")
    abstract fun findById(id: Int): Flowable<Movie>

    @Query("select * from movies")
    abstract fun getAll(): List<Movie>

    @Query("select * from movies where movieId = :id")
    abstract fun getAll(id: Int): List<Movie>

    @Query("delete from movies")
    abstract fun deleteAll()*/
}