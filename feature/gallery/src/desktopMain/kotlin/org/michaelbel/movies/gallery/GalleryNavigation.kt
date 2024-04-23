package org.michaelbel.movies.gallery

import androidx.compose.material.Text
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import moe.tlaster.precompose.navigation.Navigator
import moe.tlaster.precompose.navigation.RouteBuilder

fun Navigator.navigateToGallery(movieId: Int) {
    navigate("gallery/$movieId")
}

fun RouteBuilder.galleryGraph(
    navigateBack: () -> Unit,
) {
    scene(
        route = GalleryDestination.route
    ) {
        Text("gallery")
    }
}

fun NavController.navigateToGallery(movieId: Int) {
    navigate("gallery/$movieId")
}

fun NavGraphBuilder.galleryGraph(
    navigateBack: () -> Unit,
) {
    composable(
        route = GalleryDestination.route
    ) {
        Text("gallery")
    }
}