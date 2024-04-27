package org.michaelbel.movies.interactor

import kotlinx.coroutines.flow.Flow
import org.michaelbel.movies.persistence.database.entity.ImagePojo
import org.michaelbel.movies.persistence.database.typealiases.MovieId

interface ImageInteractor {

    fun imagesFlow(
        movieId: MovieId
    ): Flow<List<ImagePojo>>

    suspend fun images(
        movieId: MovieId
    )
}