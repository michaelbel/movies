package org.michaelbel.movies.persistence.database.ktx

import org.michaelbel.movies.network.config.formatImage
import org.michaelbel.movies.network.model.image.BackdropSize
import org.michaelbel.movies.network.model.image.LogoSize
import org.michaelbel.movies.network.model.image.PosterSize
import org.michaelbel.movies.persistence.database.entity.ImageDb
import org.michaelbel.movies.persistence.database.entity.pojo.ImagePojo
import org.michaelbel.movies.persistence.database.entity.pojo.ImageType

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

val ImagePojo.image: String
    get() = when (type) {
        ImageType.BACKDROP -> filePath.formatImage(BackdropSize.W300.size)
        ImageType.POSTER -> filePath.formatImage(PosterSize.W92.size)
        ImageType.LOGO -> filePath.formatImage(LogoSize.W45.size)
    }

val ImagePojo.original: String
    get() = when (type) {
        ImageType.BACKDROP -> filePath.formatImage(BackdropSize.ORIGINAL.size)
        ImageType.POSTER -> filePath.formatImage(PosterSize.ORIGINAL.size)
        ImageType.LOGO -> filePath.formatImage(LogoSize.ORIGINAL.size)
    }