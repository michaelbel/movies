package org.michaelbel.movies.interactor.impl

import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import org.michaelbel.movies.common.dispatchers.MoviesDispatchers
import org.michaelbel.movies.interactor.ImageInteractor
import org.michaelbel.movies.persistence.database.entity.ImageDb
import org.michaelbel.movies.repository.ImageRepository

@Singleton
internal class ImageInteractorImpl @Inject constructor(
    private val dispatchers: MoviesDispatchers,
    private val imageRepository: ImageRepository
): ImageInteractor {

    override fun imagesFlow(movieId: Int): Flow<List<ImageDb>> {
        return imageRepository.imagesFlow(movieId)
    }

    override suspend fun images(movieId: Int) {
        return withContext(dispatchers.io) {
            imageRepository.images(movieId)
        }
    }
}