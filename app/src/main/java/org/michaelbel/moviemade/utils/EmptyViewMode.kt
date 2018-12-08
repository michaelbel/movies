package org.michaelbel.moviemade.utils

import androidx.annotation.IntDef
import org.michaelbel.moviemade.utils.EmptyViewMode.Companion.MODE_NO_CONNECTION
import org.michaelbel.moviemade.utils.EmptyViewMode.Companion.MODE_NO_HISTORY
import org.michaelbel.moviemade.utils.EmptyViewMode.Companion.MODE_NO_KEYWORDS
import org.michaelbel.moviemade.utils.EmptyViewMode.Companion.MODE_NO_MOVIES
import org.michaelbel.moviemade.utils.EmptyViewMode.Companion.MODE_NO_PEOPLE
import org.michaelbel.moviemade.utils.EmptyViewMode.Companion.MODE_NO_RESULTS
import org.michaelbel.moviemade.utils.EmptyViewMode.Companion.MODE_NO_REVIEWS
import org.michaelbel.moviemade.utils.EmptyViewMode.Companion.MODE_NO_TRAILERS

@IntDef(
    MODE_NO_CONNECTION,
    MODE_NO_MOVIES,
    MODE_NO_PEOPLE,
    MODE_NO_REVIEWS,
    MODE_NO_RESULTS,
    MODE_NO_HISTORY,
    MODE_NO_TRAILERS,
    MODE_NO_KEYWORDS
)
@EmptyViewMode
annotation class EmptyViewMode {
    companion object {
        const val MODE_NO_CONNECTION = 0
        const val MODE_NO_MOVIES = 1
        const val MODE_NO_PEOPLE = 2
        const val MODE_NO_REVIEWS = 3
        const val MODE_NO_RESULTS = 4
        const val MODE_NO_HISTORY = 5
        const val MODE_NO_TRAILERS = 6
        const val MODE_NO_KEYWORDS = 7
    }
}