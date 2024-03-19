@file:Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")

package org.michaelbel.movies.gallery

import org.michaelbel.movies.navigation.MoviesNavigationDestination

actual object GalleryDestination: MoviesNavigationDestination {

    override val route: String = "gallery/{movieId}"

    override val destination: String = "gallery"
}