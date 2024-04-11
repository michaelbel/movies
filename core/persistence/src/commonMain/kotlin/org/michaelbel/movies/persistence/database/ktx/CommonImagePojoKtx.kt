package org.michaelbel.movies.persistence.database.ktx

import org.michaelbel.movies.network.config.formatImage
import org.michaelbel.movies.network.model.image.BackdropSize
import org.michaelbel.movies.network.model.image.LogoSize
import org.michaelbel.movies.network.model.image.PosterSize
import org.michaelbel.movies.persistence.database.entity.ImagePojo
import org.michaelbel.movies.persistence.database.entity.ImageType

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