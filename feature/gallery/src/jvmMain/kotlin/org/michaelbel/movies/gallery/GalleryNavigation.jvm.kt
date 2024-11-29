package org.michaelbel.movies.gallery

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import org.michaelbel.movies.gallery.ui.GalleryRoute

fun NavGraphBuilder.galleryGraph(
    navigateBack: () -> Unit,
) {
    composable<GalleryDestination> {
        GalleryRoute(
            onBackClick = navigateBack
        )
    }
}