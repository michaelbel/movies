package org.michaelbel.movies.auth

import androidx.compose.material.Text
import moe.tlaster.precompose.navigation.Navigator
import moe.tlaster.precompose.navigation.RouteBuilder

fun Navigator.navigateToAuth() {
    navigate(AuthDestination.route)
}

fun RouteBuilder.authGraph(
    navigateBack: () -> Unit
) {
    scene(
        route = AuthDestination.route,
    ) {
        Text("auth")
    }
}