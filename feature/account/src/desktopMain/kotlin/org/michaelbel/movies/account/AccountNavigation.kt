package org.michaelbel.movies.account

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.dialog
import org.michaelbel.movies.account.ui.AccountRoute

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