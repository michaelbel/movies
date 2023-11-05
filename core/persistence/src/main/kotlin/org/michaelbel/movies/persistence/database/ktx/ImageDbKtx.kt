package org.michaelbel.movies.persistence.database.ktx

import org.michaelbel.movies.network.formatImage
import org.michaelbel.movies.network.model.image.BackdropSize
import org.michaelbel.movies.network.model.image.LogoSize
import org.michaelbel.movies.network.model.image.PosterSize
import org.michaelbel.movies.persistence.database.entity.ImageDb

val ImageDb.image: String
    get() = when (type) {
        ImageDb.Type.BACKDROP -> filePath.formatImage(BackdropSize.W300.size)
        ImageDb.Type.POSTER -> filePath.formatImage(PosterSize.W92.size)
        ImageDb.Type.LOGO -> filePath.formatImage(LogoSize.W45.size)
    }

val ImageDb.original: String
    get() = when (type) {
        ImageDb.Type.BACKDROP -> filePath.formatImage(BackdropSize.ORIGINAL.size)
        ImageDb.Type.POSTER -> filePath.formatImage(PosterSize.ORIGINAL.size)
        ImageDb.Type.LOGO -> filePath.formatImage(LogoSize.ORIGINAL.size)
    }