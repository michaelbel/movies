package org.michaelbel.movies.feed.ui.common

sealed class FeedBottomTabs(
    val route: String
) {
    data object Movies: FeedBottomTabs(
        route = "Movies"
    )

    data object Series: FeedBottomTabs(
        route = "Series"
    )

    companion object {
        val VALUES = listOf(
            Movies,
            Series
        )
    }
}