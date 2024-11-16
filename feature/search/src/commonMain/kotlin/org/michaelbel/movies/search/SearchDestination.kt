package org.michaelbel.movies.search

import kotlinx.serialization.Serializable
import org.michaelbel.movies.navigation.MoviesNavigationDestination

internal object SearchDestination: MoviesNavigationDestination {

    override val route: String = "search"

    override val destination: String = "search"
}

@Serializable
object SearchRoute