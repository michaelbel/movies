package org.michaelbel.movies.details.ktx

import android.content.Context
import org.michaelbel.movies.details.R
import org.michaelbel.movies.entities.MovieDetailsData
import org.michaelbel.movies.entities.lce.ScreenState

internal val ScreenState.Content<*>.movie: MovieDetailsData
    get() = data as MovieDetailsData

internal fun ScreenState.toolbarTitle(context: Context): String {
    return when (this) {
        is ScreenState.Loading -> context.getString(R.string.details_title)
        is ScreenState.Content<*> -> movie.title
        is ScreenState.Failure -> context.getString(R.string.details_title)
    }
}