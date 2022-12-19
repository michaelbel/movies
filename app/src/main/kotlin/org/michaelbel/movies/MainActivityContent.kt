package org.michaelbel.movies

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import org.michaelbel.movies.details.navigation.DetailsDestination
import org.michaelbel.movies.details.navigation.detailsGraph
import org.michaelbel.movies.feed.navigation.FeedDestination
import org.michaelbel.movies.feed.navigation.feedGraph
import org.michaelbel.movies.settings.navigation.SettingsDestination
import org.michaelbel.movies.settings.navigation.settingsGraph

@Composable
internal fun MainActivityContent(
    navHostController: NavHostController = rememberNavController(),
    startDestination: String = FeedDestination.route
) {
    NavHost(
        navController = navHostController,
        startDestination = startDestination
    ) {
        feedGraph(
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