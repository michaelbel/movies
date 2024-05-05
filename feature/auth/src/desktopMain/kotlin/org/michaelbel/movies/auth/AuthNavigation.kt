package org.michaelbel.movies.auth

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.dialog
import org.michaelbel.movies.auth.ui.AuthRoute

fun NavController.navigateToAuth() {
    navigate(AuthDestination.route)
}

fun NavGraphBuilder.authGraph(
    navigateBack: () -> Unit
) {
    dialog(
        route = AuthDestination.route,
    ) {
        AuthRoute(
            onBackClick = navigateBack
        )
    }
}