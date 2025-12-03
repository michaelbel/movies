package org.michaelbel.movies.main

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.scene.DialogSceneStrategy
import androidx.navigation3.ui.NavDisplay
import org.michaelbel.movies.account.accountGraph
import org.michaelbel.movies.auth.authGraph
import org.michaelbel.movies.details.detailsGraph
import org.michaelbel.movies.gallery.galleryGraph
import org.michaelbel.movies.main.navigation.StartDestination
import org.michaelbel.movies.main.navigation.mainNavGraph
import org.michaelbel.movies.search.searchGraph
import org.michaelbel.movies.settings.settingsGraph
import org.michaelbel.movies.ui.ktx.ObserveAsEvents
import org.michaelbel.movies.ui.navigation.MainNavigator
import org.michaelbel.movies.ui.navigation.Navigator
import org.michaelbel.movies.ui.navigation.rememberNavigationState
import org.michaelbel.movies.ui.navigation.toEntries

@Composable
fun MainContent(
    onRequestReview: () -> Unit = {},
    onRequestUpdate: () -> Unit = {},
    navigator: Navigator? = null
) {
    val navigationState = rememberNavigationState(
        startRoute = StartDestination,
        topLevelRoutes = setOf(StartDestination)
    )
    val appNavigator = remember(navigator) { navigator ?: Navigator(navigationState) }

    val entryProvider = remember {
        entryProvider {
            authGraph()
            accountGraph()
            mainNavGraph()
            detailsGraph()
            galleryGraph()
            searchGraph()
            settingsGraph()
        }
    }

    NavDisplay(
        entries = navigationState.toEntries(entryProvider),
        onBack = { appNavigator.goBack() },
        sceneStrategy = remember { DialogSceneStrategy() },
        modifier = Modifier.fillMaxSize()
    )

    ObserveAsEvents(MainNavigator.destFlow) { dest ->
        when (dest) {
            is MainNavigator.NavigationEvent.Back -> appNavigator.goBack()
            is MainNavigator.NavigationEvent.Forward -> appNavigator.navigate(dest.destination)
            is MainNavigator.NavigationEvent.RequestReview -> onRequestReview()
            is MainNavigator.NavigationEvent.RequestUpdate -> onRequestUpdate()
        }
    }
}