package org.michaelbel.movies.persistence.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import org.michaelbel.movies.persistence.database.entity.ImageDb
import org.michaelbel.movies.persistence.database.entity.pojo.ImagePojo
import org.michaelbel.movies.persistence.database.typealiases.MovieId

/**
 * The Data Access Object for the [ImageDb] class.
 */
@Dao
interface ImageDao {

    @Query("SELECT * FROM images WHERE movieId = :movieId ORDER BY position ASC")
    fun imagesFlow(movieId: MovieId): Flow<List<ImagePojo>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(images: List<ImageDb>)
}