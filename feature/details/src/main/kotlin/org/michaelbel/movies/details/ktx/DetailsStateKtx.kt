package org.michaelbel.movies.details.ktx

import android.content.Context
import org.michaelbel.movies.details.R
import org.michaelbel.movies.details.model.DetailsState

fun DetailsState.toolbarTitle(context: Context): String {
    return when (this) {
        is DetailsState.Loading -> context.getString(R.string.details_title)
        is DetailsState.Content -> movie.title
        is DetailsState.Failure -> context.getString(R.string.details_title)
    }
}