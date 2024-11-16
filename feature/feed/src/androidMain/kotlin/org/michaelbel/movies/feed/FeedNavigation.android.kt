package org.michaelbel.movies.feed

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import org.michaelbel.movies.feed.ui.FeedRoute

fun NavGraphBuilder.feedGraph(
    navigateToSearch: () -> Unit,
    navigateToAuth: () -> Unit,
    navigateToAccount: () -> Unit,
    navigateToSettings: () -> Unit,
    navigateToDetails: (String, Int) -> Unit
) {
    composable(
        route = FeedDestination.route,
        arguments = listOf(
            navArgument("request_token") {
                type = NavType.StringType
                nullable = true
            },
            navArgument("approved") {
                type = NavType.BoolType
                defaultValue = false
            }
        ),
        deepLinks = listOf(
            navDeepLink { uriPattern = "movies://redirect_url?request_token={request_token}&approved={approved}" }
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