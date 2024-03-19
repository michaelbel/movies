@file:Suppress("EXPECT_AND_ACTUAL_IN_THE_SAME_MODULE")

package org.michaelbel.movies.settings

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navDeepLink
import org.michaelbel.movies.settings.ui.SettingsRoute
import org.michaelbel.movies.ui.shortcuts.INTENT_ACTION_SETTINGS

expect fun NavController.navigateToSettings()

expect fun NavGraphBuilder.settingsGraph(
    navigateBack: () -> Unit
)