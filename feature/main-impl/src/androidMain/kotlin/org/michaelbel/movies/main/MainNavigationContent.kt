package org.michaelbel.movies.main

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import org.michaelbel.movies.account.accountGraph
import org.michaelbel.movies.account.navigateToAccount
import org.michaelbel.movies.auth.authGraph
import org.michaelbel.movies.auth.ktx.navigateToAuth
import org.michaelbel.movies.common.ThemeData
import org.michaelbel.movies.details.detailsGraph
import org.michaelbel.movies.details.navigateToDetails
import org.michaelbel.movies.gallery.galleryGraph
import org.michaelbel.movies.gallery.navigateToGallery
import org.michaelbel.movies.main.navigation.MainDestination
import org.michaelbel.movies.main.navigation.mainGraph
import org.michaelbel.movies.search.navigateToSearch
import org.michaelbel.movies.search.searchGraph
import org.michaelbel.movies.settings.navigateToSettings
import org.michaelbel.movies.ui.theme.MoviesTheme

@Composable
fun MainNavigationContent(
    themeData: ThemeData,
    enableEdgeToEdge: (Any, Any) -> Unit
) {
    val navHostController = rememberNavController()

    MoviesTheme(
        themeData = themeData,
        enableEdgeToEdge = enableEdgeToEdge
    ) {
        NavHost(
            navController = navHostController,
            startDestination = MainDestination,
            modifier = Modifier.fillMaxSize()
        ) {
            authGraph(
                navigateBack = navHostController::popBackStack
            )
            accountGraph(
                navigateBack = navHostController::popBackStack
            )
            mainGraph(
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
        }
    }
}