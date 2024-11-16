package org.michaelbel.movies.settings

import kotlinx.serialization.Serializable
import org.michaelbel.movies.navigation.MoviesNavigationDestination

internal object SettingsDestination: MoviesNavigationDestination {

    override val route: String = "settings"

    override val destination: String = "settings"
}

@Serializable
object SettingsRoute