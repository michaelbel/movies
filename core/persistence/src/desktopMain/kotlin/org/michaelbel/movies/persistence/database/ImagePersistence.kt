@file:Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")

package org.michaelbel.movies.persistence.database

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import org.michaelbel.movies.persistence.database.entity.ImagePojo
import org.michaelbel.movies.persistence.database.typealiases.MovieId

actual class ImagePersistence internal constructor() {

    actual fun imagesFlow(
        movieId: MovieId
    ): Flow<List<ImagePojo>> {
        return emptyFlow()
    }

    actual suspend fun insert(
        images: List<ImagePojo>
    ) {}
}