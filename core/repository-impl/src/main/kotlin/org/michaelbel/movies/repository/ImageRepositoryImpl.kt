package org.michaelbel.movies.repository

import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.flow.Flow
import org.michaelbel.movies.network.tmdbApiKey
import org.michaelbel.movies.network.model.ImagesResponse
import org.michaelbel.movies.network.service.image.ImageService
import org.michaelbel.movies.persistence.database.dao.ImageDao
import org.michaelbel.movies.persistence.database.entity.ImageDb
import org.michaelbel.movies.persistence.database.ktx.imageDb

@Singleton
internal class ImageRepositoryImpl @Inject constructor(
    private val imageService: ImageService,
    private val imageDao: ImageDao
): ImageRepository {

    override fun imagesFlow(movieId: Int): Flow<List<ImageDb>> {
        return imageDao.imagesFlow(movieId)
    }

    override suspend fun images(movieId: Int) {
        val imageResponse: ImagesResponse = imageService.images(
            id = movieId,
            apiKey = tmdbApiKey
        )
        val backdrops: List<ImageDb> = imageResponse.backdrops.map { image ->
            image.imageDb(movieId, ImageDb.Type.BACKDROP)
        }
        val posters: List<ImageDb> = imageResponse.posters.map { image ->
            image.imageDb(movieId, ImageDb.Type.POSTER)
        }
        val logos: List<ImageDb> = imageResponse.logos.map { image ->
            image.imageDb(movieId, ImageDb.Type.LOGO)
        }
        imageDao.insert(backdrops + posters + logos)
    }
}