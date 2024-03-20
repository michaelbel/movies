@file:Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")

package org.michaelbel.movies.common.appearance

import org.michaelbel.movies.common.SealedString
import org.michaelbel.movies.common.appearance.exceptions.InvalidFeedViewException

actual sealed interface FeedView: SealedString {

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