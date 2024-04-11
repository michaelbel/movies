package org.michaelbel.movies.account

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