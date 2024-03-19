@file:Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")

package org.michaelbel.movies.settings

import org.michaelbel.movies.navigation.MoviesNavigationDestination

actual object SettingsDestination: MoviesNavigationDestination {

    override val route: String = "settings"

    override val destination: String = "settings"
}