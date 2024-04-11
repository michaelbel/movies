package org.michaelbel.movies.gallery

import androidx.compose.material.Text
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