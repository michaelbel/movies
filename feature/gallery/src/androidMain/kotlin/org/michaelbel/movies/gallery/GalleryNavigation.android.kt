package org.michaelbel.movies.gallery

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import org.michaelbel.movies.gallery.ui.GalleryRoute
import org.michaelbel.movies.persistence.database.typealiases.MovieId

fun NavController.navigateToGallery(movieId: MovieId) {
    navigate("gallery/$movieId")
}

fun NavGraphBuilder.galleryGraph(
    navigateBack: () -> Unit,
) {
    composable(
        route = GalleryDestination.route
    ) {
        GalleryRoute(
            onBackClick = navigateBack
        )
    }
}