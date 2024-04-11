package org.michaelbel.movies.auth

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