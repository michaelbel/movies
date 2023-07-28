package org.michaelbel.movies.feed

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import org.michaelbel.movies.feed.ui.FeedRoute

fun NavGraphBuilder.feedGraph(
    navigateToAccount: () -> Unit,
    navigateToAuth: () -> Unit,
    navigateToSettings: () -> Unit,
    navigateToDetails: (Int) -> Unit,
    onStartUpdateFlow: () -> Unit
) {
    composable(
        route = FeedDestination.route
    ) {
        FeedRoute(
            onNavigateToAccount = navigateToAccount,
            onNavigateToAuth = navigateToAuth,
            onNavigateToSettings = navigateToSettings,
            onNavigateToDetails = navigateToDetails,
            onStartUpdateFlow = onStartUpdateFlow
        )
    }
}