@file:Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")

package org.michaelbel.movies.search

import org.michaelbel.movies.navigation.MoviesNavigationDestination

actual object SearchDestination: MoviesNavigationDestination {

    override val route: String = "search"

    override val destination: String = "search"
}