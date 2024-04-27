package org.michaelbel.movies.persistence.database.ktx

import org.michaelbel.movies.network.model.Image
import org.michaelbel.movies.persistence.database.entity.ImagePojo
import org.michaelbel.movies.persistence.database.entity.ImageType
import org.michaelbel.movies.persistence.database.typealiases.MovieId
import org.michaelbel.movies.persistence.database.typealiases.Position

fun Image.imagePojo(
    movieId: MovieId,
    type: ImageType,
    position: Position
): ImagePojo {
    return ImagePojo(
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