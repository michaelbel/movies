package org.michaelbel.movies.feed

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navDeepLink
import org.michaelbel.movies.feed.ui.FeedRoute

fun NavGraphBuilder.feedGraph(
    navigateToSearch: () -> Unit,
    navigateToAuth: () -> Unit,
    navigateToAccount: () -> Unit,
    navigateToSettings: () -> Unit,
    navigateToDetails: (String, Int) -> Unit
) {
    composable<FeedDestination>(
        deepLinks = listOf(
            navDeepLink { uriPattern = "movies://redirect_url?request_token={requestToken}&approved={approved}" }
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