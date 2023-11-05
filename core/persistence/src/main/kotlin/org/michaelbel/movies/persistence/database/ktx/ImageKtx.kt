package org.michaelbel.movies.persistence.database.ktx

import org.michaelbel.movies.network.model.Image
import org.michaelbel.movies.persistence.database.entity.ImageDb

fun Image.imageDb(movieId: Int, type: ImageDb.Type): ImageDb {
    return ImageDb(
        movieId = movieId,
        filePath = filePath,
        type = type,
        width = width,
        height = height,
        aspectRatio = aspectRatio,
        voteAverage = voteAverage,
        voteCount = voteCount,
        lang = lang
    )
}