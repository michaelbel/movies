package org.michaelbel.movies.settings

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navDeepLink
import org.michaelbel.movies.settings.ui.SettingsRoute
import org.michaelbel.movies.ui.shortcuts.INTENT_ACTION_SETTINGS

fun NavGraphBuilder.settingsGraph2(
    navigateBack: () -> Unit,
    onRequestReview: () -> Unit,
    onRequestUpdate: () -> Unit
) {
    composable<SettingsDestination>(
        deepLinks = listOf(navDeepLink { uriPattern = INTENT_ACTION_SETTINGS })
    ) {
        SettingsRoute(
            onBackClick = navigateBack,
            onRequestReview = onRequestReview,
            onRequestUpdate = onRequestUpdate
        )
    }
}