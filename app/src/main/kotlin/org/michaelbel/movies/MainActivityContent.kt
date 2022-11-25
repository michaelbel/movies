package org.michaelbel.movies

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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
    modifier: Modifier = Modifier,
    navHostController: NavHostController = rememberNavController(),
    startDestination: String = FeedDestination.route
) {
    Scaffold { paddingValues: PaddingValues ->
        NavHost(
            navController = navHostController,
            startDestination = startDestination,
            modifier = modifier
                .padding(paddingValues)
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
}