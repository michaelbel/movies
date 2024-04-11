package org.michaelbel.movies.interactor

import kotlinx.coroutines.flow.Flow
import org.michaelbel.movies.persistence.database.entity.ImagePojo

interface ImageInteractor {

    fun imagesFlow(
        movieId: Int
    ): Flow<List<ImagePojo>>

    suspend fun images(
        movieId: Int
    )
}