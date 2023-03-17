package org.michaelbel.movies.auth

import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.dialog
import org.michaelbel.movies.auth.ui.AccountRoute
import org.michaelbel.movies.navigation.MoviesNavigationDestination

object AccountDestination: MoviesNavigationDestination {

    override val route: String = "account"

    override val destination: String = "account"
}

fun NavGraphBuilder.accountGraph(
    onBackClick: () -> Unit
) {
    dialog(
        route = AccountDestination.route,
        dialogProperties = DialogProperties(
            usePlatformDefaultWidth = false
        )
    ) {
        AccountRoute(
            onBackClick = onBackClick
        )
    }
}