package org.michaelbel.movies.details

import androidx.compose.material.Text
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import moe.tlaster.precompose.navigation.Navigator
import moe.tlaster.precompose.navigation.RouteBuilder

fun Navigator.navigateToDetails(movieList: String, movieId: Int) {
    navigate("movie?movieList=$movieList&movieId=$movieId")
}

fun RouteBuilder.detailsGraph(
    navigateBack: () -> Unit,
    navigateToGallery: (Int) -> Unit
) {
    scene(
        route = DetailsDestination.route
    ) {
        Text("details")
    }
}

fun NavController.navigateToDetails(movieList: String, movieId: Int) {
    navigate("movie?movieList=$movieList&movieId=$movieId")
}

fun NavGraphBuilder.detailsGraph(
    navigateBack: () -> Unit,
    navigateToGallery: (Int) -> Unit
) {
    composable(
        route = DetailsDestination.route
    ) {
        Text("details")
    }
}