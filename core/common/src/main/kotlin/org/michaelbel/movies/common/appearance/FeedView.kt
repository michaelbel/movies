package org.michaelbel.movies.common.appearance

import org.michaelbel.movies.common.appearance.exceptions.InvalidFeedViewException

sealed interface FeedView {

    data object List: FeedView

    data object Grid: FeedView

    companion object {
        fun transform(name: String): FeedView {
            return when (name) {
                List.toString() -> List
                Grid.toString() -> Grid
                else -> throw InvalidFeedViewException
            }
        }
    }
}