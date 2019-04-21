package org.michaelbel.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import io.reactivex.Completable
import io.reactivex.Flowable
import org.michaelbel.data.Review
import org.michaelbel.data.local.BaseDao

@Dao
abstract class ReviewsDao: BaseDao<Review>() {

    @Query("select * from reviews where id = :id")
    abstract fun findById(id: Int): Flowable<Review>

    @Query("select * from reviews")
    abstract fun getAll(): List<Review>

    @Query("select * from reviews where movieId = :id")
    abstract fun getAll(id: Int): Flowable<List<Review>>

    @Query("delete from reviews")
    abstract fun deleteAll()

    @Insert(onConflict = REPLACE)
    abstract fun insert(items: List<Review>): Completable
}