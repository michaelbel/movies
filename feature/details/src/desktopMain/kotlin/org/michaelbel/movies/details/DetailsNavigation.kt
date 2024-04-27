package org.michaelbel.movies.details

import androidx.compose.material.Text
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import moe.tlaster.precompose.navigation.Navigator
import moe.tlaster.precompose.navigation.RouteBuilder
import org.michaelbel.movies.persistence.database.typealiases.MovieId
import org.michaelbel.movies.persistence.database.typealiases.PagingKey

fun Navigator.navigateToDetails(pagingKey: PagingKey, movieId: MovieId) {
    navigate("movie?movieList=$pagingKey&movieId=$movieId")
}

fun RouteBuilder.detailsGraph(
    navigateBack: () -> Unit,
    navigateToGallery: (MovieId) -> Unit
) {
    scene(
        route = DetailsDestination.route
    ) {
        Text("details")
    }
}

fun NavController.navigateToDetails(pagingKey: PagingKey, movieId: MovieId) {
    navigate("movie?movieList=$pagingKey&movieId=$movieId")
}

fun NavGraphBuilder.detailsGraph(
    navigateBack: () -> Unit,
    navigateToGallery: (MovieId) -> Unit
) {
    composable(
        route = DetailsDestination.route
    ) {
        Text("details")
    }
}