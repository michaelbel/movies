package org.michaelbel.movies.settings

import moe.tlaster.precompose.navigation.Navigator
import moe.tlaster.precompose.navigation.RouteBuilder
import org.michaelbel.movies.settings.ui.SettingsRoute

fun Navigator.navigateToSettings() {
    navigate(SettingsDestination.route)
}

fun RouteBuilder.settingsGraph(
    navigateBack: () -> Unit
) {
    scene(
        route = SettingsDestination.route
    ) {
        SettingsRoute(
            onBackClick = navigateBack
        )
    }
}