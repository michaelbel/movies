package org.michaelbel.movies.main.mainnav

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navDeepLink
import org.michaelbel.movies.main.navigation.MainDestination

fun NavGraphBuilder.mainGraph(
    navigateToSearch: () -> Unit,
    navigateToAuth: () -> Unit,
    navigateToAccount: () -> Unit,
    navigateToSettings: () -> Unit,
    onRequestReview: () -> Unit,
    onRequestUpdate: () -> Unit,
    navigateToDetails: (String, Int) -> Unit
) {
    composable<MainDestination>(
        deepLinks = listOf(
            navDeepLink { uriPattern = "movies://redirect_url?request_token={requestToken}&approved={approved}" }
        )
    ) {
        MainNavRoute(
            navigateToSearch = navigateToSearch,
            navigateToAuth = navigateToAuth,
            navigateToAccount = navigateToAccount,
            navigateToSettings = navigateToSettings,
            onRequestReview = onRequestReview,
            onRequestUpdate = onRequestUpdate,
            navigateToDetails = navigateToDetails
        )
    }
}