package org.michaelbel.movies.account

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.dialog
import moe.tlaster.precompose.navigation.Navigator
import moe.tlaster.precompose.navigation.RouteBuilder
import org.michaelbel.movies.account.ui.AccountRoute

fun Navigator.navigateToAccount() {
    navigate(AccountDestination.route)
}

fun RouteBuilder.accountGraph(
    navigateBack: () -> Unit
) {
    dialog(
        route = AccountDestination.route
    ) {
        AccountRoute(
            onBackClick = navigateBack
        )
    }
}

fun NavController.navigateToAccount() {
    navigate(AccountDestination.route)
}

fun NavGraphBuilder.accountGraph(
    navigateBack: () -> Unit
) {
    dialog(
        route = AccountDestination.route
    ) {
        AccountRoute(
            onBackClick = navigateBack
        )
    }
}