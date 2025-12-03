package org.michaelbel.movies.main.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import org.michaelbel.movies.feed.FeedDestination
import org.michaelbel.movies.feed.feedGraph
import org.michaelbel.movies.ui.navigation.Navigator
import org.michaelbel.movies.ui.navigation.rememberNavigationState
import org.michaelbel.movies.ui.navigation.toEntries

@Composable
actual fun NavContent() {
    val startRoute = remember { FeedDestination() }
    val navigationState = rememberNavigationState(
        startRoute = startRoute,
        topLevelRoutes = setOf(startRoute)
    )
    val navigator = remember { Navigator(navigationState) }
    val entryProvider = remember {
        entryProvider {
            feedGraph(
                navigateToSearch = {},
                navigateToAuth = {},
                navigateToAccount = {},
                navigateToSettings = {},
                navigateToDetails = { _, _ -> }
            )
        }
    }

    NavDisplay(
        entries = navigationState.toEntries(entryProvider),
        onBack = { navigator.goBack() },
        modifier = Modifier.fillMaxSize()
    )
}
