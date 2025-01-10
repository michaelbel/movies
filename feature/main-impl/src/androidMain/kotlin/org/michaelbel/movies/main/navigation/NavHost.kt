package org.michaelbel.movies.main.navigation

import androidx.navigation.NavGraphBuilder
import org.michaelbel.movies.main.mainnav.mainGraph

actual val StartDestination: Any = MainDestination

actual fun NavGraphBuilder.mainNavGraph(
    navigateToSearch: () -> Unit,
    navigateToAuth: () -> Unit,
    navigateToAccount: () -> Unit,
    navigateToSettings: () -> Unit,
    onRequestReview: () -> Unit,
    onRequestUpdate: () -> Unit,
    navigateToDetails: (String, Int) -> Unit
) {
    mainGraph(
        navigateToSearch = navigateToSearch,
        navigateToAuth = navigateToAuth,
        navigateToAccount = navigateToAccount,
        navigateToSettings = navigateToSettings,
        navigateToDetails = navigateToDetails,
        onRequestReview = onRequestReview,
        onRequestUpdate = onRequestUpdate
    )
}