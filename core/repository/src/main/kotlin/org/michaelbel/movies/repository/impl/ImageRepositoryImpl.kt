package org.michaelbel.movies.repository.impl

import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.flow.Flow
import org.michaelbel.movies.network.model.ImagesResponse
import org.michaelbel.movies.network.service.image.ImageService
import org.michaelbel.movies.persistence.database.dao.ImageDao
import org.michaelbel.movies.persistence.database.entity.ImageDb
import org.michaelbel.movies.persistence.database.ktx.imageDb
import org.michaelbel.movies.repository.ImageRepository

@Singleton
internal class ImageRepositoryImpl @Inject constructor(
    private val imageService: ImageService,
    private val imageDao: ImageDao
): ImageRepository {

    override fun imagesFlow(movieId: Int): Flow<List<ImageDb>> {
        return imageDao.imagesFlow(movieId)
    }

    override suspend fun images(movieId: Int) {
        val imageResponse: ImagesResponse = imageService.images(movieId)
        val posters: List<ImageDb> = imageResponse.posters.mapIndexed { index, image ->
            image.imageDb(
                movieId = movieId,
                type = ImageDb.Type.POSTER,
                position = index
            )
        }
        val backdrops: List<ImageDb> = imageResponse.backdrops.mapIndexed { index, image ->
            image.imageDb(
                movieId = movieId,
                type = ImageDb.Type.BACKDROP,
                position = posters.count().plus(index)
            )
        }
        val logos: List<ImageDb> = imageResponse.logos.mapIndexed { index, image ->
            image.imageDb(
                movieId = movieId,
                type = ImageDb.Type.LOGO,
                position = posters.count().plus(backdrops.count()).plus(index)
            )
        }
        imageDao.insert(posters + backdrops + logos)
    }
}