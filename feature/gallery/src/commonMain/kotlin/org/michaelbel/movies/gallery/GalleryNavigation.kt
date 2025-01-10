package org.michaelbel.movies.gallery

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import org.michaelbel.movies.gallery.ui.GalleryRoute
import org.michaelbel.movies.persistence.database.typealiases.MovieId

fun NavGraphBuilder.galleryGraph(
    navigateBack: () -> Unit,
) {
    composable<GalleryDestination> {
        GalleryRoute(
            onBackClick = navigateBack
        )
    }
}

fun NavController.navigateToGallery(movieId: MovieId) {
    navigate(GalleryDestination(movieId))
}