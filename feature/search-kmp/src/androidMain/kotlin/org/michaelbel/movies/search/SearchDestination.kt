package org.michaelbel.movies.search

import org.michaelbel.movies.navigation.MoviesNavigationDestination

internal object SearchDestination: MoviesNavigationDestination {

    override val route: String = "search"

    override val destination: String = "search"
}