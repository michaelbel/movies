package org.michaelbel.movies.feed

import androidx.navigation.NavDeepLink
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import org.michaelbel.movies.feed.ui.FeedRoute

private const val INTENT_ACTION_AUTH_REDIRECT_URL = "movies://redirect_url?request_token={request_token}&approved={approved}"

private val FEED_AUTH_REDIRECT_NAV_DEEP_LINK: NavDeepLink = navDeepLink {
    uriPattern = INTENT_ACTION_AUTH_REDIRECT_URL
}

fun NavGraphBuilder.feedGraph(
    navigateToSearch: () -> Unit,
    navigateToAuth: () -> Unit,
    navigateToAccount: () -> Unit,
    navigateToSettings: () -> Unit,
    navigateToDetails: (Int) -> Unit
) {
    composable(
        route = FeedDestination.route,
        arguments = listOf(
            navArgument("request_token") { type = NavType.StringType },
            navArgument("approved") { type = NavType.BoolType },
        ),
        deepLinks = listOf(FEED_AUTH_REDIRECT_NAV_DEEP_LINK)
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