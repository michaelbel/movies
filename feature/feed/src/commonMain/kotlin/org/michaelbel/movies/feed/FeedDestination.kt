package org.michaelbel.movies.feed

import org.michaelbel.movies.navigation.MoviesNavigationDestination

object FeedDestination: MoviesNavigationDestination {

    override val route: String = "feed"

    override val destination: String = "feed"
}