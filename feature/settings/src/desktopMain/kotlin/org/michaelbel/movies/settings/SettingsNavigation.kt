package org.michaelbel.movies.settings

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
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

fun NavController.navigateToSettings() {
    navigate(SettingsDestination.route)
}

fun NavGraphBuilder.settingsGraph(
    navigateBack: () -> Unit
) {
    composable(
        route = SettingsDestination.route
    ) {
        SettingsRoute(
            onBackClick = navigateBack
        )
    }
}