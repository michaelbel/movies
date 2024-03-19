@file:Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")

package org.michaelbel.movies.feed

import org.michaelbel.movies.navigation.MoviesNavigationDestination

actual object FeedDestination: MoviesNavigationDestination {

    override val route: String = "feed"

    override val destination: String = "feed"
}