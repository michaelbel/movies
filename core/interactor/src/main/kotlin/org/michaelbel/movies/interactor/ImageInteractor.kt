package org.michaelbel.movies.interactor

import kotlinx.coroutines.flow.Flow
import org.michaelbel.movies.persistence.database.entity.ImageDb

interface ImageInteractor {

    fun imagesFlow(movieId: Int): Flow<List<ImageDb>>

    suspend fun images(movieId: Int)
}