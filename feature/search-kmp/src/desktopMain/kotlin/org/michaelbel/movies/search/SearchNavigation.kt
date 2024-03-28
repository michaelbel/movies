package org.michaelbel.movies.search

import androidx.compose.material.Text
import moe.tlaster.precompose.navigation.Navigator
import moe.tlaster.precompose.navigation.RouteBuilder

fun Navigator.navigateToSearch() {
    navigate(SearchDestination.route)
}

fun RouteBuilder.searchGraph(
    navigateBack: () -> Unit,
    navigateToDetails: (String, Int) -> Unit,
) {
    scene(
        route = SearchDestination.route
    ) {
        Text("Feed")
    }
}