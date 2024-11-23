package org.michaelbel.movies.settings

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import org.michaelbel.movies.settings.ui.SettingsRoute

fun NavGraphBuilder.settingsGraph(
    navigateBack: () -> Unit
) {
    composable<SettingsDestination> {
        SettingsRoute(
            onBackClick = navigateBack
        )
    }
}