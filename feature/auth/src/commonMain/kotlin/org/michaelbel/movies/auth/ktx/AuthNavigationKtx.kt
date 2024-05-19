package org.michaelbel.movies.auth.ktx

import androidx.compose.runtime.Composable
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.dialog
import org.michaelbel.movies.auth.AuthDestination
import org.michaelbel.movies.auth.USE_PLATFORM_DEFAULT_WIDTH

fun NavController.navigateToAuth() {
    navigate(AuthDestination.route)
}

internal fun NavGraphBuilder.authGraphInternal(
    content: @Composable (NavBackStackEntry) -> Unit
) {
    dialog(
        route = AuthDestination.route,
        dialogProperties = DialogProperties(
            usePlatformDefaultWidth = USE_PLATFORM_DEFAULT_WIDTH
        ),
        content = content
    )
}