package org.michaelbel.movies.gallery

import androidx.compose.foundation.clickable
import androidx.compose.material.Text
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
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
        Text(
            text = "Gallery",
            modifier = Modifier.clickable { navigateBack() }
        )
    }
}