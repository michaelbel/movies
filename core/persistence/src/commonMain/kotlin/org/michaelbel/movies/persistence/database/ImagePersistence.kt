package org.michaelbel.movies.persistence.database

import kotlinx.coroutines.flow.Flow
import org.michaelbel.movies.persistence.database.entity.pojo.ImagePojo
import org.michaelbel.movies.persistence.database.ktx.imageDb
import org.michaelbel.movies.persistence.database.typealiases.MovieId

class ImagePersistence internal constructor(
    private val moviesDatabase: MoviesDatabase
) {

    fun imagesFlow(movieId: MovieId): Flow<List<ImagePojo>> {
        return moviesDatabase.imageDao.imagesFlow(movieId)
    }

    suspend fun insert(images: List<ImagePojo>) {
        moviesDatabase.imageDao.insert(images.map(ImagePojo::imageDb))
    }
}