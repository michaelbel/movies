package org.michaelbel.movies.settings

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import org.michaelbel.movies.settings.ui.SettingsRoute

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