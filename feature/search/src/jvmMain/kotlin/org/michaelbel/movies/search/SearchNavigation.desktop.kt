package org.michaelbel.movies.search

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import org.michaelbel.movies.search.ui.SearchRoute

fun NavController.navigateToSearch() {
    navigate(SearchDestination.route)
}

fun NavGraphBuilder.searchGraph(
    navigateBack: () -> Unit,
    navigateToDetails: (String, Int) -> Unit,
) {
    composable(
        route = SearchDestination.route
    ) {
        SearchRoute(
            onBackClick = navigateBack,
            onNavigateToDetails = navigateToDetails
        )
    }
}