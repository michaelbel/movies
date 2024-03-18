@file:Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")

package org.michaelbel.movies.account

import org.michaelbel.movies.navigation.MoviesNavigationDestination

actual object AccountDestination: MoviesNavigationDestination {

    override val route: String = "account"

    override val destination: String = "account"
}