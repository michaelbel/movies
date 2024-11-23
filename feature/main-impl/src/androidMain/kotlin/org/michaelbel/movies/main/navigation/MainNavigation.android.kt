package org.michaelbel.movies.main.navigation

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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navDeepLink
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import org.michaelbel.movies.feed.FeedDestination
import org.michaelbel.movies.feed.feedGraph
import org.michaelbel.movies.settings.SettingsDestination
import org.michaelbel.movies.settings.settingsGraph
import org.michaelbel.movies.ui.icons.MoviesIcons
import org.michaelbel.movies.ui.ktx.ObserveAsEvents

fun NavGraphBuilder.mainGraph(
    navigateToSearch: () -> Unit,
    navigateToAuth: () -> Unit,
    navigateToAccount: () -> Unit,
    navigateToSettings: () -> Unit,
    navigateToDetails: (String, Int) -> Unit
) {
    composable<MainDestination>(
        deepLinks = listOf(
            navDeepLink { uriPattern = "movies://redirect_url?request_token={requestToken}&approved={approved}" }
        )
    ) {
        val viewModel: MainNavViewModel = koinViewModel()

        val navHostController = rememberNavController()
        val layoutDirection = LocalLayoutDirection.current
        var selectedTab: Any by remember { mutableStateOf(FeedDestination()) }
        val scope = rememberCoroutineScope()
        val snackbarHostState = remember { SnackbarHostState() }

        Scaffold(
            modifier = Modifier.fillMaxSize(),
            bottomBar = {
                NavigationBar(
                    modifier = Modifier,
                    containerColor = MaterialTheme.colorScheme.inversePrimary
                ) {
                    NavigationBarItem(
                        selected = selectedTab is FeedDestination,
                        onClick = { selectedTab = FeedDestination() },
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
                        selected = selectedTab is SettingsDestination,
                        onClick = { selectedTab = SettingsDestination },
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
            NavHost(
                navController = navHostController,
                startDestination = selectedTab,
                modifier = Modifier.padding(
                    start = innerPadding.calculateStartPadding(layoutDirection),
                    top = 0.dp,
                    end = innerPadding.calculateEndPadding(layoutDirection),
                    bottom = innerPadding.calculateBottomPadding()
                )
            ) {
                feedGraph(
                    navigateToSearch = navigateToSearch,
                    navigateToAuth = navigateToAuth,
                    navigateToAccount = navigateToAccount,
                    navigateToSettings = navigateToSettings,
                    navigateToDetails = navigateToDetails
                )
                settingsGraph(
                    navigateBack = {}
                )
            }
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
}