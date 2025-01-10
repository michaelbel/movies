package org.michaelbel.movies.main.navigation

import androidx.navigation.NavGraphBuilder
import org.michaelbel.movies.feed.FeedDestination
import org.michaelbel.movies.feed.feedGraph

actual val StartDestination: Any = FeedDestination()

actual fun NavGraphBuilder.mainNavGraph(
    navigateToSearch: () -> Unit,
    navigateToAuth: () -> Unit,
    navigateToAccount: () -> Unit,
    navigateToSettings: () -> Unit,
    onRequestReview: () -> Unit,
    onRequestUpdate: () -> Unit,
    navigateToDetails: (String, Int) -> Unit
) {
    feedGraph(
        navigateToSearch = navigateToSearch,
        navigateToAuth = navigateToAuth,
        navigateToAccount = navigateToAccount,
        navigateToSettings = navigateToSettings,
        navigateToDetails = navigateToDetails
    )
}