package org.michaelbel.movies.auth

import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.dialog
import org.michaelbel.movies.auth.ui.AuthRoute

actual fun NavController.navigateToAuth() {
    navigate(AuthDestination.route)
}

actual fun NavGraphBuilder.authGraph(
    navigateBack: () -> Unit
) {
    dialog(
        route = AuthDestination.route,
        dialogProperties = DialogProperties(
            usePlatformDefaultWidth = false
        )
    ) {
        AuthRoute(
            onBackClick = navigateBack
        )
    }
}