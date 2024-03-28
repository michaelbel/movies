package org.michaelbel.movies.repository

import kotlinx.coroutines.flow.Flow
import org.michaelbel.movies.persistence.database.entity.ImagePojo

interface ImageRepository {

    fun imagesFlow(
        movieId: Int
    ): Flow<List<ImagePojo>>

    suspend fun images(
        movieId: Int
    )
}