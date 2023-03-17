package org.michaelbel.movies.feed

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import org.michaelbel.movies.feed.ui.FeedRoute
import org.michaelbel.movies.navigation.MoviesNavigationDestination

object FeedDestination: MoviesNavigationDestination {

    override val route: String = "feed"

    override val destination: String = "feed"
}

fun NavGraphBuilder.feedGraph(
    navigateToAccount: () -> Unit,
    navigateToAuth: () -> Unit,
    navigateToSettings: () -> Unit,
    navigateToDetails: (Int) -> Unit
) {
    composable(
        route = FeedDestination.route
    ) {
        FeedRoute(
            onNavigateToAccount = navigateToAccount,
            onNavigateToAuth = navigateToAuth,
            onNavigateToSettings = navigateToSettings,
            onNavigateToDetails = navigateToDetails
        )
    }
}