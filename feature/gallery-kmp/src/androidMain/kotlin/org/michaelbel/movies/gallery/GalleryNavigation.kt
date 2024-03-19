package org.michaelbel.movies.gallery

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import org.michaelbel.movies.gallery.ui.GalleryRoute

actual fun NavController.navigateToGallery(movieId: Int) {
    navigate("gallery/$movieId")
}

actual fun NavGraphBuilder.galleryGraph(
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