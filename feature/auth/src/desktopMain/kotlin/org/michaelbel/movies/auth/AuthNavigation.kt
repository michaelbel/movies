package org.michaelbel.movies.auth

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.dialog
import moe.tlaster.precompose.navigation.Navigator
import moe.tlaster.precompose.navigation.RouteBuilder
import org.michaelbel.movies.auth.ui.AuthRoute

fun Navigator.navigateToAuth() {
    navigate(AuthDestination.route)
}

fun RouteBuilder.authGraph(
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