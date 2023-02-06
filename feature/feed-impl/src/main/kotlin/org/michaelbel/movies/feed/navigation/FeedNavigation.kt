package org.michaelbel.movies.feed.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import org.michaelbel.movies.feed.ui.FeedRoute
import org.michaelbel.movies.navigation.MoviesNavigationDestination

object FeedDestination: MoviesNavigationDestination {

    override val route: String = "feed"

    override val destination: String = "feed"
}

fun NavGraphBuilder.feedGraph(
    navigateToSettings: () -> Unit,
    navigateToDetails: (Int) -> Unit
) {
    composable(
        route = FeedDestination.route
    ) {
        FeedRoute(
            onNavigateToSettings = navigateToSettings,
            onNavigateToDetails = navigateToDetails
        )
    }
}