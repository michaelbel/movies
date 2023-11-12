package org.michaelbel.movies.gallery.ktx

import org.michaelbel.movies.gallery_impl.R
import org.michaelbel.movies.persistence.database.entity.ImageDb

internal val ImageDb.Type.nameRes: Int
    get() = when (this) {
        ImageDb.Type.POSTER -> R.string.gallery_poster
        ImageDb.Type.BACKDROP -> R.string.gallery_backdrop
        ImageDb.Type.LOGO -> R.string.gallery_logo
    }