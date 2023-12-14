package org.michaelbel.movies.feed

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import org.michaelbel.movies.feed.ui.FeedRoute

fun NavGraphBuilder.feedGraph(
    navigateToSearch: () -> Unit,
    navigateToAuth: () -> Unit,
    navigateToAccount: () -> Unit,
    navigateToSettings: () -> Unit,
    navigateToDetails: (Int) -> Unit
) {
    composable(
        route = FeedDestination.route
    ) {
        FeedRoute(
            onNavigateToSearch = navigateToSearch,
            onNavigateToAccount = navigateToAccount,
            onNavigateToAuth = navigateToAuth,
            onNavigateToSettings = navigateToSettings,
            onNavigateToDetails = navigateToDetails
        )
    }
}