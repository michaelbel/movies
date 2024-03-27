@file:Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")

package org.michaelbel.movies.persistence.database

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import org.michaelbel.movies.persistence.database.entity.ImagePojo

actual class ImagePersistence internal constructor() {

    fun imagesFlow(movieId: Int): Flow<List<ImagePojo>> {
        return emptyFlow()
    }

    suspend fun insert(images: List<ImagePojo>) {}
}