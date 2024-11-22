package org.michaelbel.movies.main.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import org.michaelbel.movies.feed.FeedDestination
import org.michaelbel.movies.feed.feedGraph

@Composable
actual fun NavContent(
    navHostController: NavHostController
) {
    NavHost(
        navController = navHostController,
        startDestination = FeedDestination(),
        modifier = Modifier.fillMaxSize()
    ) {
        feedGraph(
            navigateToSearch = {},
            navigateToAuth = {},
            navigateToAccount = {},
            navigateToSettings = {},
            navigateToDetails = { _,_ -> }
        )
    }
}