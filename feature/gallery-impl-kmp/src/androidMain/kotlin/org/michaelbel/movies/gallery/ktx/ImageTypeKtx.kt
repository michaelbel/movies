package org.michaelbel.movies.gallery.ktx

import androidx.annotation.StringRes
import org.michaelbel.movies.gallery_impl_kmp.R
import org.michaelbel.movies.persistence.database.entity.ImageDb

internal val ImageDb.Type.nameRes: Int
    @StringRes get() = when (this) {
        ImageDb.Type.POSTER -> R.string.gallery_poster
        ImageDb.Type.BACKDROP -> R.string.gallery_backdrop
        ImageDb.Type.LOGO -> R.string.gallery_logo
    }