package org.michaelbel.movies.repository

import kotlinx.coroutines.flow.Flow
import org.michaelbel.movies.persistence.database.entity.ImagePojo
import org.michaelbel.movies.persistence.database.typealiases.MovieId

interface ImageRepository {

    fun imagesFlow(
        movieId: MovieId
    ): Flow<List<ImagePojo>>

    suspend fun images(
        movieId: MovieId
    )
}