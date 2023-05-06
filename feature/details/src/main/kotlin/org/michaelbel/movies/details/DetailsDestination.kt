package org.michaelbel.movies.details

import org.michaelbel.movies.navigation.MoviesNavigationDestination

internal object DetailsDestination: MoviesNavigationDestination {

    const val movieIdArg = "movieId"

    override val route: String = "movie/{$movieIdArg}"

    override val destination: String = "movie"

    fun createNavigationRoute(movieId: Int): String {
        return "movie/$movieId"
    }
}