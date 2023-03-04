package org.michaelbel.movies.auth

import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.dialog
import org.michaelbel.movies.auth.ui.AuthRoute
import org.michaelbel.movies.navigation.MoviesNavigationDestination

object AuthDestination: MoviesNavigationDestination {

    override val route: String = "auth"

    override val destination: String = "auth"
}

fun NavGraphBuilder.authGraph(
    onBackClick: () -> Unit
) {
    dialog(
        route = AuthDestination.route,
        dialogProperties = DialogProperties(
            usePlatformDefaultWidth = false
        )
    ) {
        AuthRoute(
            onBackClick = onBackClick
        )
    }
}