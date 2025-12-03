package org.michaelbel.movies.main.mainnav

import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.dp
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import kotlinx.coroutines.launch
import org.koin.compose.viewmodel.koinViewModel
import org.michaelbel.movies.feed.feedGraph
import org.michaelbel.movies.feed.navigation.FeedDestination
import org.michaelbel.movies.settings.settingsGraph
import org.michaelbel.movies.ui.icons.MoviesIcons
import org.michaelbel.movies.ui.ktx.ObserveAsEvents
import org.michaelbel.movies.ui.navigation.Navigator
import org.michaelbel.movies.ui.navigation.SettingsDestination
import org.michaelbel.movies.ui.navigation.rememberNavigationState
import org.michaelbel.movies.ui.navigation.toEntries

@Composable
fun MainNavRoute(
    requestToken: String?,
    approved: Boolean?,
    viewModel: MainNavViewModel = koinViewModel()
) {
    val layoutDirection = LocalLayoutDirection.current
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    val feedRoute = remember(requestToken, approved) {
        FeedDestination(requestToken = requestToken, approved = approved ?: false)
    }

    val navigationState = rememberNavigationState(
        startRoute = feedRoute,
        topLevelRoutes = setOf(feedRoute, SettingsDestination)
    )
    val navigator = remember { Navigator(navigationState) }

    val entryProvider = remember {
        entryProvider {
            feedGraph()
            settingsGraph()
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            NavigationBar(
                modifier = Modifier,
                containerColor = MaterialTheme.colorScheme.inversePrimary
            ) {
                NavigationBarItem(
                    selected = navigationState.topLevelRoute == feedRoute,
                    onClick = { navigator.navigate(feedRoute) },
                    icon = {
                        Icon(
                            imageVector = MoviesIcons.GridView,
                            contentDescription = null
                        )
                    },
                    label = {
                        Text(
                            text = "Feed"
                        )
                    }
                )

                NavigationBarItem(
                    selected = navigationState.topLevelRoute == SettingsDestination,
                    onClick = { navigator.navigate(SettingsDestination) },
                    icon = {
                        Icon(
                            imageVector = MoviesIcons.Settings,
                            contentDescription = null
                        )
                    },
                    label = {
                        Text(
                            text = "Settings"
                        )
                    }
                )
            }
        },
        snackbarHost = {
            SnackbarHost(
                hostState = snackbarHostState
            )
        }
    ) { innerPadding ->
        NavDisplay(
            entries = navigationState.toEntries(entryProvider),
            onBack = { navigator.goBack() },
            modifier = Modifier.padding(
                start = innerPadding.calculateStartPadding(layoutDirection),
                top = 0.dp,
                end = innerPadding.calculateEndPadding(layoutDirection),
                bottom = innerPadding.calculateBottomPadding()
            )
        )
    }

    LaunchedEffect(feedRoute.requestToken, feedRoute.approved) {
        viewModel.onRedirect(feedRoute.requestToken, feedRoute.approved)
    }

    ObserveAsEvents(
        flow = viewModel.snackbarMessage,
        key1 = snackbarHostState
    ) { message ->
        scope.launch {
            snackbarHostState.run {
                currentSnackbarData?.dismiss()
                showSnackbar(message)
            }
        }
    }
}
