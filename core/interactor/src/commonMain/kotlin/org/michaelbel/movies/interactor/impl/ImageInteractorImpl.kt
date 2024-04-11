package org.michaelbel.movies.interactor.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import org.michaelbel.movies.common.dispatchers.MoviesDispatchers
import org.michaelbel.movies.interactor.ImageInteractor
import org.michaelbel.movies.persistence.database.entity.ImagePojo
import org.michaelbel.movies.repository.ImageRepository

internal class ImageInteractorImpl(
    private val dispatchers: MoviesDispatchers,
    private val imageRepository: ImageRepository
): ImageInteractor {

    override fun imagesFlow(
        movieId: Int
    ): Flow<List<ImagePojo>> {
        return imageRepository.imagesFlow(movieId)
    }

    override suspend fun images(
        movieId: Int
    ) {
        return withContext(dispatchers.io) { imageRepository.images(movieId) }
    }
}