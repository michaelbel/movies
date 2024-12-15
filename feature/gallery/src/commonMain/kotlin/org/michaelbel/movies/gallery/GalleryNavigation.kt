package org.michaelbel.movies.gallery

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import org.michaelbel.movies.persistence.database.typealiases.MovieId

expect fun NavGraphBuilder.galleryGraph(
    navigateBack: () -> Unit,
)

fun NavController.navigateToGallery(movieId: MovieId) {
    navigate(GalleryDestination(movieId))
}