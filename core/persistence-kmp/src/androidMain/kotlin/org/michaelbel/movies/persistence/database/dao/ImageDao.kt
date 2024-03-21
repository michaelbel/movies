package org.michaelbel.movies.persistence.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import org.michaelbel.movies.persistence.database.entity.ImageDb

/**
 * The Data Access Object for the [ImageDb] class.
 */
@Dao
internal interface ImageDao {

    @Query("SELECT * FROM images WHERE movieId = :movieId ORDER BY position ASC")
    fun imagesFlow(movieId: Int): Flow<List<ImageDb>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(images: List<ImageDb>)
}