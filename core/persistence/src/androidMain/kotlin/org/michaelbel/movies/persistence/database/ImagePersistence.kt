@file:Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")

package org.michaelbel.movies.persistence.database

import kotlinx.coroutines.flow.Flow
import org.michaelbel.movies.persistence.database.dao.ImageDao
import org.michaelbel.movies.persistence.database.entity.ImagePojo
import org.michaelbel.movies.persistence.database.ktx.imageDb

actual class ImagePersistence internal constructor(
    private val imageDao: ImageDao
) {

    actual fun imagesFlow(movieId: Int): Flow<List<ImagePojo>> {
        return imageDao.imagesFlow(movieId)
    }

    actual suspend fun insert(images: List<ImagePojo>) {
        imageDao.insert(images.map(ImagePojo::imageDb))
    }
}