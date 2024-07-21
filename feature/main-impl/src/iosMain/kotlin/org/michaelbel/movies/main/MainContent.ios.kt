package org.michaelbel.movies.main

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import org.michaelbel.movies.auth.authGraph
import org.michaelbel.movies.feed.FeedDestination

@Composable
fun MainContent(
    navHostController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navHostController,
        startDestination = FeedDestination.route
    ) {
        authGraph(
            navigateBack = navHostController::popBackStack
        )
        /*accountGraph(
            navigateBack = navHostController::popBackStack
        )
        feedGraph(
            navigateToSearch = navHostController::navigateToSearch,
            navigateToAuth = navHostController::navigateToAuth,
            navigateToAccount = navHostController::navigateToAccount,
            navigateToSettings = navHostController::navigateToSettings,
            navigateToDetails = navHostController::navigateToDetails
        )
        detailsGraph(
            navigateBack = navHostController::popBackStack,
            navigateToGallery = navHostController::navigateToGallery
        )
        galleryGraph(
            navigateBack = navHostController::popBackStack
        )
        searchGraph(
            navigateBack = navHostController::popBackStack,
            navigateToDetails = navHostController::navigateToDetails,
        )
        settingsGraph(
            navigateBack = navHostController::popBackStack
        )*/
    }
}