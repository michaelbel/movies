package org.michaelbel.movies.auth

import kotlinx.serialization.Serializable
import org.michaelbel.movies.navigation.MoviesNavigationDestination

internal object AuthDestination: MoviesNavigationDestination {

    override val route: String = "auth"

    override val destination: String = "auth"
}

@Serializable
object AuthRoute