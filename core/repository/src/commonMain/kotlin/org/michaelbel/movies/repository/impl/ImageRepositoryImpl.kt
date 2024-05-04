package org.michaelbel.movies.repository.impl

import kotlinx.coroutines.flow.Flow
import org.michaelbel.movies.network.MovieNetworkService
import org.michaelbel.movies.persistence.database.ImagePersistence
import org.michaelbel.movies.persistence.database.entity.pojo.ImagePojo
import org.michaelbel.movies.persistence.database.entity.pojo.ImageType
import org.michaelbel.movies.persistence.database.ktx.imagePojo
import org.michaelbel.movies.persistence.database.typealiases.MovieId
import org.michaelbel.movies.repository.ImageRepository

internal class ImageRepositoryImpl(
    private val movieNetworkService: MovieNetworkService,
    private val imagePersistence: ImagePersistence
): ImageRepository {

    override fun imagesFlow(
        movieId: MovieId
    ): Flow<List<ImagePojo>> {
        return imagePersistence.imagesFlow(movieId)
    }

    override suspend fun images(
        movieId: MovieId
    ) {
        val imageResponse = movieNetworkService.images(movieId)
        val posters = imageResponse.posters.mapIndexed { index, image ->
            image.imagePojo(
                movieId = movieId,
                type = ImageType.POSTER,
                position = index
            )
        }
        val backdrops = imageResponse.backdrops.mapIndexed { index, image ->
            image.imagePojo(
                movieId = movieId,
                type = ImageType.BACKDROP,
                position = posters.count().plus(index)
            )
        }
        val logos = imageResponse.logos.mapIndexed { index, image ->
            image.imagePojo(
                movieId = movieId,
                type = ImageType.LOGO,
                position = posters.count().plus(backdrops.count()).plus(index)
            )
        }
        imagePersistence.insert(posters + backdrops + logos)
    }
}