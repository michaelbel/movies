package org.michaelbel.movies.main.navigation

import androidx.navigation.NavGraphBuilder

expect val StartDestination: Any

expect fun NavGraphBuilder.mainNavGraph(
    navigateToSearch: () -> Unit,
    navigateToAuth: () -> Unit,
    navigateToAccount: () -> Unit,
    navigateToSettings: () -> Unit,
    onRequestReview: () -> Unit,
    onRequestUpdate: () -> Unit,
    navigateToDetails: (String, Int) -> Unit
)