package org.michaelbel.movies

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
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