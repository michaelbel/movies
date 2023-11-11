package org.michaelbel.movies

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import org.michaelbel.movies.auth.accountGraph
import org.michaelbel.movies.auth.authGraph
import org.michaelbel.movies.auth.navigateToAccount
import org.michaelbel.movies.auth.navigateToAuth
import org.michaelbel.movies.common.theme.AppTheme
import org.michaelbel.movies.details.detailsGraph
import org.michaelbel.movies.details.navigateToDetails
import org.michaelbel.movies.feed.FeedDestination
import org.michaelbel.movies.feed.feedGraph
import org.michaelbel.movies.gallery.galleryGraph
import org.michaelbel.movies.gallery.navigateToGallery
import org.michaelbel.movies.navigation.ktx.addOnDestinationChangedListener
import org.michaelbel.movies.settings.navigateToSettings
import org.michaelbel.movies.settings.settingsGraph
import org.michaelbel.movies.ui.theme.MoviesTheme

@Composable
internal fun MainActivityContent(
    viewModel: MainViewModel = hiltViewModel(),
    onStartUpdateFlow: () -> Unit
) {
    val currentTheme: AppTheme by viewModel.currentTheme.collectAsStateWithLifecycle()
    val dynamicColors: Boolean by viewModel.dynamicColors.collectAsStateWithLifecycle()
    val navHostController: NavHostController = rememberNavController().apply {
        addOnDestinationChangedListener(viewModel::analyticsTrackDestination)
    }

    MoviesTheme(
        theme = currentTheme,
        dynamicColors = dynamicColors
    ) {
        NavHost(
            navController = navHostController,
            startDestination = FeedDestination.route
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
                navigateBack = navHostController::popBackStack,
                navigateToGallery = navHostController::navigateToGallery
            )
            galleryGraph(
                navigateBack = navHostController::popBackStack
            )
            settingsGraph(
                navigateBack = navHostController::popBackStack
            )
        }
    }
}