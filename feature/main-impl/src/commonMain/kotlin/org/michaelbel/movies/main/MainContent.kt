package org.michaelbel.movies.main

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import org.michaelbel.movies.account.accountGraph
import org.michaelbel.movies.account.navigateToAccount
import org.michaelbel.movies.auth.authGraph
import org.michaelbel.movies.auth.navigateToAuth
import org.michaelbel.movies.details.detailsGraph
import org.michaelbel.movies.details.navigateToDetails
import org.michaelbel.movies.gallery.galleryGraph
import org.michaelbel.movies.gallery.navigateToGallery
import org.michaelbel.movies.main.navigation.StartDestination
import org.michaelbel.movies.main.navigation.mainNavGraph
import org.michaelbel.movies.search.navigateToSearch
import org.michaelbel.movies.search.searchGraph
import org.michaelbel.movies.settings.navigateToSettings

@Composable
fun MainContent(
    onRequestReview: () -> Unit = {},
    onRequestUpdate: () -> Unit = {},
    navHostController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navHostController,
        startDestination = StartDestination,
        modifier = Modifier.fillMaxSize()
    ) {
        authGraph(
            navigateBack = navHostController::popBackStack
        )
        accountGraph(
            navigateBack = navHostController::popBackStack
        )
        mainNavGraph(
            navigateToSearch = navHostController::navigateToSearch,
            navigateToAuth = navHostController::navigateToAuth,
            navigateToAccount = navHostController::navigateToAccount,
            navigateToSettings = navHostController::navigateToSettings,
            navigateToDetails = navHostController::navigateToDetails,
            onRequestReview = onRequestReview,
            onRequestUpdate = onRequestUpdate
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
    }
}