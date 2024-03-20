package org.michaelbel.movies.repository.impl

import kotlinx.coroutines.flow.Flow
import org.michaelbel.movies.network.MovieNetworkService
import org.michaelbel.movies.persistence.database.ImagePersistence
import org.michaelbel.movies.persistence.database.entity.ImageDb
import org.michaelbel.movies.persistence.database.ktx.imageDb
import org.michaelbel.movies.repository.ImageRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class ImageRepositoryImpl @Inject constructor(
    private val movieNetworkService: MovieNetworkService,
    private val imagePersistence: ImagePersistence
): ImageRepository {

    override fun imagesFlow(movieId: Int): Flow<List<ImageDb>> {
        return imagePersistence.imagesFlow(movieId)
    }

    override suspend fun images(movieId: Int) {
        val imageResponse = movieNetworkService.images(movieId)
        val posters = imageResponse.posters.mapIndexed { index, image ->
            image.imageDb(
                movieId = movieId,
                type = ImageDb.Type.POSTER,
                position = index
            )
        }
        val backdrops = imageResponse.backdrops.mapIndexed { index, image ->
            image.imageDb(
                movieId = movieId,
                type = ImageDb.Type.BACKDROP,
                position = posters.count().plus(index)
            )
        }
        val logos = imageResponse.logos.mapIndexed { index, image ->
            image.imageDb(
                movieId = movieId,
                type = ImageDb.Type.LOGO,
                position = posters.count().plus(backdrops.count()).plus(index)
            )
        }
        imagePersistence.insert(posters + backdrops + logos)
    }
}