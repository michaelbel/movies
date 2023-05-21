package org.michaelbel.movies.auth

import org.michaelbel.movies.navigation.MoviesNavigationDestination

internal object AccountDestination: MoviesNavigationDestination {

    override val route: String = "account"

    override val destination: String = "account"
}