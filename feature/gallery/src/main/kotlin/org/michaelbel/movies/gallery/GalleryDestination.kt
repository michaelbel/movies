package org.michaelbel.movies.gallery

import org.michaelbel.movies.navigation.MoviesNavigationDestination

object GalleryDestination: MoviesNavigationDestination {

    const val movieIdArg = "movieId"

    override val route: String = "gallery/{$movieIdArg}"

    override val destination: String = "gallery"

    fun createNavigationRoute(movieId: Int): String {
        return "gallery/$movieId"
    }
}