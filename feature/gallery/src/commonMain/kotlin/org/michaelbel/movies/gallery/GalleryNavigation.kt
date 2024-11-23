package org.michaelbel.movies.gallery

import androidx.navigation.NavController
import org.michaelbel.movies.persistence.database.typealiases.MovieId

fun NavController.navigateToGallery(movieId: MovieId) {
    navigate(GalleryDestination(movieId))
}