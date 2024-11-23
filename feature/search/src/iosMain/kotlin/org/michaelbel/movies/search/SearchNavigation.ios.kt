package org.michaelbel.movies.search

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import org.michaelbel.movies.search.ui.SearchRoute

fun NavGraphBuilder.searchGraph(
    navigateBack: () -> Unit,
    navigateToDetails: (String, Int) -> Unit,
) {
    composable<SearchDestination> {
        SearchRoute(
            onBackClick = navigateBack,
            onNavigateToDetails = navigateToDetails
        )
    }
}