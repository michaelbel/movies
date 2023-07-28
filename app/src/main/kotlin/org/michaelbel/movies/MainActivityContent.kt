package org.michaelbel.movies

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import org.michaelbel.movies.auth.accountGraph
import org.michaelbel.movies.auth.authGraph
import org.michaelbel.movies.auth.navigateToAccount
import org.michaelbel.movies.auth.navigateToAuth
import org.michaelbel.movies.details.detailsGraph
import org.michaelbel.movies.details.navigateToDetails
import org.michaelbel.movies.feed.FeedDestination
import org.michaelbel.movies.feed.feedGraph
import org.michaelbel.movies.settings.navigateToSettings
import org.michaelbel.movies.settings.settingsGraph

@Composable
internal fun MainActivityContent(
    navHostController: NavHostController = rememberNavController(),
    startDestination: String = FeedDestination.route,
    onStartUpdateFlow: () -> Unit
) {
    NavHost(
        navController = navHostController,
        startDestination = startDestination
    ) {
        authGraph(
            navigateBack = navHostController::popBackStack
        )
        accountGraph(
            navigateBack = navHostController::popBackStack
        )
        feedGraph(
            navigateToAuth = navHostController::navigateToAuth,
            navigateToAccount = navHostController::navigateToAccount,
            navigateToSettings = navHostController::navigateToSettings,
            navigateToDetails = navHostController::navigateToDetails,
            onStartUpdateFlow = onStartUpdateFlow
        )
        detailsGraph(
            navigateBack = navHostController::popBackStack
        )
        settingsGraph(
            navigateBack = navHostController::popBackStack
        )
    }
}