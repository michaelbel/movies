package org.michaelbel.movies.persistence.database.ktx

import org.michaelbel.movies.network.model.Image
import org.michaelbel.movies.persistence.database.entity.ImageDb

actual fun Image.imageDb(
    movieId: Int,
    type: ImageDb.Type,
    position: Int
): ImageDb {
    return ImageDb(
        movieId = movieId,
        filePath = filePath,
        type = type,
        width = width,
        height = height,
        aspectRatio = aspectRatio,
        voteAverage = voteAverage,
        voteCount = voteCount,
        lang = lang,
        position = position
    )
}