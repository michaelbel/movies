package org.michaelbel.movies.search

import androidx.compose.foundation.clickable
import androidx.compose.material.Text
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

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
        Text(
            text = "Feed",
            modifier = Modifier.clickable { navigateBack() }
        )
    }
}