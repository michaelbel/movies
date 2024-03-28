package org.michaelbel.movies.persistence.database.ktx

import org.michaelbel.movies.persistence.database.entity.ImageDb
import org.michaelbel.movies.persistence.database.entity.ImagePojo

internal val ImagePojo.imageDb: ImageDb
    get() = ImageDb(
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