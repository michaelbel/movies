package org.michaelbel.movies.search

import androidx.navigation.NavController
import androidx.navigation.NavDeepLink
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navDeepLink
import org.michaelbel.movies.search.ui.SearchRoute
import org.michaelbel.movies.ui.shortcuts.INTENT_ACTION_SEARCH

fun NavController.navigateToSearch() {
    navigate(SearchDestination.route)
}

fun NavGraphBuilder.searchGraph(
    navigateBack: () -> Unit,
    navigateToDetails: (String, Int) -> Unit,
) {
    composable(
        route = SearchDestination.route,
        deepLinks = listOf(
            navDeepLink { uriPattern = INTENT_ACTION_SEARCH }
        )
    ) {
        SearchRoute(
            onBackClick = navigateBack,
            onNavigateToDetails = navigateToDetails
        )
    }
}