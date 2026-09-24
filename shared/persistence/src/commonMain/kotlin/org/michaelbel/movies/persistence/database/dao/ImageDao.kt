package org.michaelbel.movies.persistence.database.dao

import androidx.room3.Dao
import androidx.room3.Query
import androidx.room3.Upsert
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
    fun selectFlow(movieId: MovieId): Flow<List<ImagePojo>>

    @Upsert
    suspend fun upsert(images: List<ImageDb>)
}
