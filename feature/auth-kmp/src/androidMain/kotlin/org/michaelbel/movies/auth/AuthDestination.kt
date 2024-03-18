@file:Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")

package org.michaelbel.movies.auth

import org.michaelbel.movies.navigation.MoviesNavigationDestination

actual object AuthDestination: MoviesNavigationDestination {

    override val route: String = "auth"

    override val destination: String = "auth"
}