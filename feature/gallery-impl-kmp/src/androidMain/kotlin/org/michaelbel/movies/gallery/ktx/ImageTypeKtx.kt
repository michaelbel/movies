package org.michaelbel.movies.gallery.ktx

import androidx.annotation.StringRes
import org.michaelbel.movies.gallery_impl_kmp.R
import org.michaelbel.movies.persistence.database.entity.ImageType

internal val ImageType.nameRes: Int
    @StringRes get() = when (this) {
        ImageType.POSTER -> R.string.gallery_poster
        ImageType.BACKDROP -> R.string.gallery_backdrop
        ImageType.LOGO -> R.string.gallery_logo
    }