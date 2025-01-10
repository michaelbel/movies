package org.michaelbel.movies.auth

import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.dialog
import org.michaelbel.movies.auth.ui.AuthRoute
import org.michaelbel.movies.ui.ktx.USE_PLATFORM_DEFAULT_WIDTH

fun NavGraphBuilder.authGraph(
    navigateBack: () -> Unit
) {
    dialog<AuthDestination>(
        dialogProperties = DialogProperties(
            usePlatformDefaultWidth = USE_PLATFORM_DEFAULT_WIDTH
        )
    ) {
        AuthRoute(
            onBackClick = navigateBack
        )
    }
}

fun NavController.navigateToAuth() {
    navigate(AuthDestination)
}