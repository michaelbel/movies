@file:Suppress("EXPECT_AND_ACTUAL_IN_THE_SAME_MODULE")

package org.michaelbel.movies.persistence.database.ktx

import org.michaelbel.movies.network.model.Image
import org.michaelbel.movies.persistence.database.entity.ImageDb

expect fun Image.imageDb(
    movieId: Int,
    type: ImageDb.Type,
    position: Int
): ImageDb