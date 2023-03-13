package org.michaelbel.movies

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import org.michaelbel.movies.auth.AccountDestination
import org.michaelbel.movies.auth.AuthDestination
import org.michaelbel.movies.auth.accountGraph
import org.michaelbel.movies.auth.authGraph
import org.michaelbel.movies.details.DetailsDestination
import org.michaelbel.movies.details.detailsGraph
import org.michaelbel.movies.feed.FeedDestination
import org.michaelbel.movies.feed.feedGraph
import org.michaelbel.movies.settings.SettingsDestination
import org.michaelbel.movies.settings.settingsGraph

@Composable
internal fun MainActivityContent(
    navHostController: NavHostController = rememberNavController(),
    startDestination: String = FeedDestination.route
) {
    NavHost(
        navController = navHostController,
        startDestination = startDestination
    ) {
        authGraph(
            onBackClick = navHostController::popBackStack
        )
        accountGraph(
            onBackClick = navHostController::popBackStack
        )
        feedGraph(
            navigateToAuth = {
                navHostController.navigate(AuthDestination.route)
            },
            navigateToAccount = {
                navHostController.navigate(AccountDestination.route)
            },
            navigateToSettings = {
                navHostController.navigate(SettingsDestination.route)
            },
            navigateToDetails = { movieId ->
                navHostController.navigate(DetailsDestination.createNavigationRoute(movieId))
            }
        )
        detailsGraph(
            onBackClick = navHostController::popBackStack
        )
        settingsGraph(
            onBackClick = navHostController::popBackStack
        )
    }
}