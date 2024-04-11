package org.michaelbel.movies.gallery

import org.michaelbel.movies.navigation.MoviesNavigationDestination

internal object GalleryDestination: MoviesNavigationDestination {

    override val route: String = "gallery/{movieId}"

    override val destination: String = "gallery"
}