package org.michaelbel.movies.feed

import moe.tlaster.precompose.navigation.RouteBuilder
import org.michaelbel.movies.feed.ui.FeedRoute

fun RouteBuilder.feedGraph(
    navigateToSearch: () -> Unit,
    navigateToAuth: () -> Unit,
    navigateToAccount: () -> Unit,
    navigateToSettings: () -> Unit,
    navigateToDetails: (String, Int) -> Unit
) {
    scene(
        route = FeedDestination.route,
        deepLinks = listOf(
            "movies://redirect_url?request_token={request_token}&approved={approved}"
        )
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