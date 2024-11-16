package org.michaelbel.movies.gallery

import kotlinx.serialization.Serializable
import org.michaelbel.movies.navigation.MoviesNavigationDestination

internal object GalleryDestination: MoviesNavigationDestination {

    override val route: String = "gallery/{movieId}"

    override val destination: String = "gallery"
}

@Serializable
class GalleryRoute(
    val movieId: Int
)