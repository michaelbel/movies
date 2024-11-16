package org.michaelbel.movies.settings

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navDeepLink
import org.michaelbel.movies.settings.ui.SettingsRoute
import org.michaelbel.movies.ui.shortcuts.INTENT_ACTION_SETTINGS

fun NavController.navigateToSettings() {
    navigate(SettingsDestination)
}

fun NavGraphBuilder.settingsGraph(
    navigateBack: () -> Unit
) {
    composable<SettingsDestination>(
        deepLinks = listOf(
            navDeepLink { uriPattern = INTENT_ACTION_SETTINGS }
        )
    ) {
        SettingsRoute(
            onBackClick = navigateBack
        )
    }
}