@file:Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")

package org.michaelbel.movies.persistence.database

import kotlinx.coroutines.flow.Flow
import org.michaelbel.movies.persistence.database.dao.ImageDao
import org.michaelbel.movies.persistence.database.entity.ImageDb
import javax.inject.Inject

actual class ImagePersistence @Inject internal constructor(
    private val imageDao: ImageDao
) {

    fun imagesFlow(movieId: Int): Flow<List<ImageDb>> {
        return imageDao.imagesFlow(movieId)
    }

    suspend fun insert(images: List<ImageDb>) {
        imageDao.insert(images)
    }
}