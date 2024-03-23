package org.michaelbel.movies.common.appearance

import org.michaelbel.movies.common.SealedString
import org.michaelbel.movies.common.appearance.exceptions.InvalidFeedViewException

sealed interface FeedView: SealedString {

    data object FeedList: FeedView

    data object FeedGrid: FeedView

    companion object {
        val VALUES = listOf(
            FeedList,
            FeedGrid
        )

        fun transform(name: String): FeedView {
            return when (name) {
                FeedList.toString() -> FeedList
                FeedGrid.toString() -> FeedGrid
                else -> throw InvalidFeedViewException
            }
        }
    }
}