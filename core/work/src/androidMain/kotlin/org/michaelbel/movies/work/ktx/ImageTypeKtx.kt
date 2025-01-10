package org.michaelbel.movies.work.ktx

import androidx.annotation.StringRes
import org.michaelbel.movies.persistence.database.entity.pojo.ImageType
import org.michaelbel.movies.work.R

internal val ImageType.nameRes: Int
    @StringRes get() = when (this) {
        ImageType.POSTER -> R.string.gallery_poster
        ImageType.BACKDROP -> R.string.gallery_backdrop
        ImageType.LOGO -> R.string.gallery_logo
    }