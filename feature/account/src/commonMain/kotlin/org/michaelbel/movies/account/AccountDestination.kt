package org.michaelbel.movies.account

import kotlinx.serialization.Serializable
import org.michaelbel.movies.navigation.MoviesNavigationDestination

internal object AccountDestination: MoviesNavigationDestination {

    override val route: String = "account"

    override val destination: String = "account"
}

@Serializable
object AccountRoute