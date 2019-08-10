package org.michaelbel.data.local.dao

import androidx.room.*
import org.michaelbel.data.local.model.ReviewLocal

@Dao
abstract class ReviewsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(review: ReviewLocal)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(vararg reviews: ReviewLocal)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insert(reviews: ArrayList<ReviewLocal>)

    @Update
    abstract fun update(review: ReviewLocal)

    @Delete
    abstract fun delete(review: ReviewLocal)

    @Query("select * from reviews")
    abstract fun getAll(): List<ReviewLocal>

    /*@Query("select * from reviews where review_id = :id")
    abstract fun getAll(id: Long): List<ReviewLocal>*/

    @Query("delete from reviews")
    abstract fun deleteAll()
}