package org.michaelbel.movies.settings

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import org.michaelbel.movies.settings.ui.SettingsRoute

fun NavController.navigateToSettings() {
    navigate(SettingsDestination)
}

fun NavGraphBuilder.settingsGraph(
    navigateBack: () -> Unit
) {
    composable<SettingsDestination> {
        SettingsRoute(
            onBackClick = navigateBack
        )
    }
}