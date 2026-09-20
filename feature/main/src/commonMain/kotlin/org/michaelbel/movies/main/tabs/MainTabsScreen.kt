@file:OptIn(
    ExperimentalMaterial3Api::class,
    ExperimentalMaterial3ExpressiveApi::class,
    ExperimentalMaterial3AdaptiveApi::class
)

package org.michaelbel.movies.main.tabs

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationItemColors
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.NavigationRailItemDefaults
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.ToggleButtonDefaults
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteItem
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffoldLayout
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffoldValue
import androidx.compose.material3.adaptive.navigationsuite.rememberNavigationSuiteScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.saveable.rememberSerializable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import androidx.savedstate.compose.serialization.serializers.SnapshotStateListSerializer
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import org.michaelbel.movies.fave.FaveScreen
import org.michaelbel.movies.feed.FeedScreen
import org.michaelbel.movies.main.event.MainEvent
import org.michaelbel.movies.main.tabs.event.MainTabsEvent
import org.michaelbel.movies.main.tabs.event.MainTabsEventManager
import org.michaelbel.movies.main.tabs.intent.MainTabsIntent
import org.michaelbel.movies.main.tabs.model.MainTabsModel
import org.michaelbel.movies.persistence.database.ktx.letters
import org.michaelbel.movies.settings.SettingsScreen
import org.michaelbel.movies.ui.ObserveAsEvents
import org.michaelbel.movies.ui.accessibility.MoviesContentDescription
import org.michaelbel.movies.ui.collectAsStateCommon
import org.michaelbel.movies.ui.compose.AccountAvatar
import org.michaelbel.movies.ui.fadePredictiveTransitionSpec
import org.michaelbel.movies.ui.fadeTransitionSpec
import org.michaelbel.movies.ui.icons.MoviesIcons
import org.michaelbel.movies.ui.isNavigationBar
import org.michaelbel.movies.ui.isShortNavigationBarMedium
import org.michaelbel.movies.ui.isWideNavigationRailCollapsed
import org.michaelbel.movies.ui.isWideNavigationRailExpanded
import org.michaelbel.movies.ui.modifierDisplayCutoutWindowInsets
import org.michaelbel.movies.ui.navigation.AppRoute
import org.michaelbel.movies.ui.navigation.FaveDestination
import org.michaelbel.movies.ui.navigation.FeedDestination
import org.michaelbel.movies.ui.navigation.SettingsDestination
import org.michaelbel.movies.ui.navigationSuiteType
import org.michaelbel.movies.ui.strings.MoviesStrings

@Composable
fun MainTabsScreen(
    feedDestination: FeedDestination,
    viewModel: MainTabsViewModel = koinViewModel()
) {
    val state by viewModel.stateFlow.collectAsStateCommon()

    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    val authFailureMessage = stringResource(MoviesStrings.feed_auth_failure)
    val authSuccessMessage = stringResource(MoviesStrings.feed_auth_success)

    val backStack: MutableList<AppRoute> = rememberSerializable(serializer = SnapshotStateListSerializer()) {
        mutableStateListOf(feedDestination)
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        MainTabsScreenContent(
            state = state,
            dispatch = viewModel::dispatch,
            backStack = backStack,
            feedDestination = feedDestination
        )

        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }

    LaunchedEffect(
        feedDestination.mainDestination.requestToken,
        feedDestination.mainDestination.approved
    ) {
        viewModel.dispatch(
            MainTabsIntent.HandleRedirect(
                feedDestination.mainDestination.requestToken,
                feedDestination.mainDestination.approved
            )
        )
    }

    ObserveAsEvents(
        flow = MainTabsEventManager.eventFlow
    ) { event ->
        when (event) {
            MainEvent.OpenFeed -> backStack[backStack.lastIndex] = feedDestination
            MainEvent.OpenFave -> backStack[backStack.lastIndex] = FaveDestination
            MainEvent.OpenSettings -> backStack[backStack.lastIndex] = SettingsDestination
        }
    }

    ObserveAsEvents(
        flow = viewModel.eventFlow,
        key1 = snackbarHostState,
        key2 = authFailureMessage to authSuccessMessage
    ) { event ->
        when (event) {
            is MainTabsEvent.ShowSnackbar -> {
                snackbarHostState.currentSnackbarData?.dismiss()
                scope.launch {
                    val message = when (event.message) {
                        MoviesStrings.feed_auth_failure -> authFailureMessage
                        MoviesStrings.feed_auth_success -> authSuccessMessage
                        else -> return@launch
                    }
                    snackbarHostState.showSnackbar(message)
                }
            }
        }
    }
}

@Composable
private fun MainTabsScreenContent(
    state: MainTabsModel,
    dispatch: (MainTabsIntent) -> Unit,
    backStack: MutableList<AppRoute>,
    feedDestination: FeedDestination
) {
    val openSearch = feedDestination.mainDestination.openSearch
    var isFeedSearchActive by rememberSaveable { mutableStateOf(openSearch) }
    var openSearchOnFeedStart by rememberSaveable { mutableStateOf(openSearch) }
    val currentDestination = backStack.lastOrNull()
    val shouldShowNavigation = currentDestination != feedDestination || !isFeedSearchActive

    val navigationSuiteScaffoldState = rememberNavigationSuiteScaffoldState(
        initialValue = when {
            shouldShowNavigation -> NavigationSuiteScaffoldValue.Visible
            else -> NavigationSuiteScaffoldValue.Hidden
        }
    )
    val toggleButtonColors = ToggleButtonDefaults.colors()
    val navigationItemColors = NavigationItemColors(
        selectedIconColor = toggleButtonColors.checkedContentColor,
        selectedTextColor = when {
            isShortNavigationBarMedium -> toggleButtonColors.checkedContentColor
            else -> toggleButtonColors.checkedContainerColor
        },
        selectedIndicatorColor = toggleButtonColors.checkedContainerColor,
        unselectedIconColor = toggleButtonColors.contentColor,
        unselectedTextColor = toggleButtonColors.contentColor,
        disabledIconColor = toggleButtonColors.disabledContentColor,
        disabledTextColor = toggleButtonColors.disabledContentColor
    )
    val navigationRailItemColors = NavigationRailItemDefaults.colors(
        selectedIconColor = toggleButtonColors.checkedContentColor,
        selectedTextColor = toggleButtonColors.checkedContentColor,
        indicatorColor = toggleButtonColors.checkedContainerColor,
        unselectedIconColor = toggleButtonColors.contentColor,
        unselectedTextColor = toggleButtonColors.contentColor,
        disabledIconColor = toggleButtonColors.disabledContentColor,
        disabledTextColor = toggleButtonColors.disabledContentColor
    )

    NavigationSuiteScaffoldLayout(
        navigationSuite = {
            when {
                isNavigationBar -> {
                    NavigationBar(
                        containerColor = MaterialTheme.colorScheme.primaryContainer
                    ) {
                        NavigationSuiteItem(
                            selected = currentDestination == feedDestination,
                            onClick = { dispatch(MainTabsIntent.FeedClick) },
                            icon = {
                                Icon(
                                    imageVector = MoviesIcons.GridView,
                                    contentDescription = stringResource(MoviesStrings.main_nav_feed)
                                )
                            },
                            label = {
                                Text(
                                    text = stringResource(MoviesStrings.main_nav_feed)
                                )
                            },
                            modifier = Modifier.weight(1F),
                            colors = navigationItemColors
                        )

                        if (state.isFaveFeatureEnabled) {
                            NavigationSuiteItem(
                                selected = currentDestination == FaveDestination,
                                onClick = { dispatch(MainTabsIntent.FaveClick) },
                                icon = {
                                    Icon(
                                        imageVector = MoviesIcons.Favorite,
                                        contentDescription = stringResource(MoviesStrings.main_nav_fave)
                                    )
                                },
                                label = {
                                    Text(
                                        text = stringResource(MoviesStrings.main_nav_fave)
                                    )
                                },
                                modifier = Modifier.weight(1F),
                                colors = navigationItemColors
                            )
                        }

                        NavigationSuiteItem(
                            selected = currentDestination == SettingsDestination,
                            onClick = { dispatch(MainTabsIntent.SettingsClick) },
                            icon = {
                                Icon(
                                    imageVector = MoviesIcons.Settings,
                                    contentDescription = stringResource(MoviesStrings.main_nav_settings)
                                )
                            },
                            label = {
                                Text(
                                    text = stringResource(MoviesStrings.main_nav_settings)
                                )
                            },
                            modifier = Modifier.weight(1F),
                            colors = navigationItemColors
                        )
                    }
                }
                isWideNavigationRailCollapsed -> {
                    Column(
                        modifier = Modifier
                            .fillMaxHeight()
                            .width(80.dp)
                            .background(MaterialTheme.colorScheme.primaryContainer)
                            .padding(bottom = 16.dp)
                    ) {
                        Column(
                            modifier = modifierDisplayCutoutWindowInsets
                                .weight(1F)
                                .fillMaxWidth(),
                            verticalArrangement = Arrangement.spacedBy(4.dp, Alignment.CenterVertically),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            NavigationRailItem(
                                selected = currentDestination == feedDestination,
                                onClick = { dispatch(MainTabsIntent.FeedClick) },
                                icon = {
                                    Icon(
                                        imageVector = MoviesIcons.GridView,
                                        contentDescription = stringResource(MoviesStrings.main_nav_feed)
                                    )
                                },
                                colors = navigationRailItemColors
                            )

                            if (state.isFaveFeatureEnabled) {
                                NavigationRailItem(
                                    selected = currentDestination == FaveDestination,
                                    onClick = { dispatch(MainTabsIntent.FaveClick) },
                                    icon = {
                                        Icon(
                                            imageVector = MoviesIcons.Favorite,
                                            contentDescription = stringResource(MoviesStrings.main_nav_fave)
                                        )
                                    },
                                    colors = navigationRailItemColors
                                )
                            }

                            NavigationRailItem(
                                selected = currentDestination == SettingsDestination,
                                onClick = { dispatch(MainTabsIntent.SettingsClick) },
                                icon = {
                                    Icon(
                                        imageVector = MoviesIcons.Settings,
                                        contentDescription = stringResource(MoviesStrings.main_nav_settings)
                                    )
                                },
                                colors = navigationRailItemColors
                            )
                        }

                        NavigationRailItem(
                            selected = false,
                            onClick = {
                                dispatch(
                                    if (state.isAuthorized) MainTabsIntent.AccountClick
                                    else MainTabsIntent.AuthClick
                                )
                            },
                            icon = {
                                when {
                                    state.isAuthorized -> {
                                        AccountAvatar(
                                            account = state.accountPojo,
                                            fontSize = if (state.accountPojo.letters.length == 1) 16.sp else 13.sp,
                                            modifier = Modifier.size(IconButtonDefaults.largeIconSize)
                                        )
                                    }
                                    else -> {
                                        Icon(
                                            imageVector = MoviesIcons.AccountCircle,
                                            contentDescription = stringResource(MoviesContentDescription.AccountIcon)
                                        )
                                    }
                                }
                            },
                            colors = navigationRailItemColors
                        )
                    }
                }
                isWideNavigationRailExpanded -> {
                    Column(
                        modifier = Modifier
                            .fillMaxHeight()
                            .width(192.dp)
                            .background(MaterialTheme.colorScheme.primaryContainer)
                            .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
                    ) {
                        Spacer(
                            modifier = Modifier.weight(1F)
                        )

                        Column(
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Row(
                                modifier = modifierDisplayCutoutWindowInsets
                                    .fillMaxWidth()
                                    .height(56.dp)
                                    .clip(RoundedCornerShape(16.dp))
                                    .background(if (currentDestination == feedDestination) toggleButtonColors.checkedContainerColor else Color.Transparent)
                                    .clickable { dispatch(MainTabsIntent.FeedClick) }
                                    .padding(horizontal = 16.dp),
                                horizontalArrangement = Arrangement.spacedBy(ToggleButtonDefaults.IconSpacing),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = MoviesIcons.GridView,
                                    contentDescription = stringResource(MoviesStrings.main_nav_feed),
                                    modifier = Modifier.size(IconButtonDefaults.smallIconSize),
                                    tint = if (currentDestination == feedDestination) navigationRailItemColors.selectedIconColor else navigationRailItemColors.unselectedIconColor
                                )

                                Text(
                                    text = stringResource(MoviesStrings.main_nav_feed),
                                    maxLines = 1,
                                    softWrap = false,
                                    overflow = TextOverflow.Clip,
                                    style = MaterialTheme.typography.titleSmallEmphasized.copy(
                                        color = if (currentDestination == feedDestination) navigationRailItemColors.selectedTextColor else navigationRailItemColors.unselectedTextColor,
                                        letterSpacing = .4.sp
                                    )
                                )
                            }

                            if (state.isFaveFeatureEnabled) {
                                Row(
                                    modifier = modifierDisplayCutoutWindowInsets
                                        .fillMaxWidth()
                                        .height(56.dp)
                                        .clip(RoundedCornerShape(16.dp))
                                        .background(if (currentDestination == FaveDestination) toggleButtonColors.checkedContainerColor else Color.Transparent)
                                        .clickable { dispatch(MainTabsIntent.FaveClick) }
                                        .padding(horizontal = 16.dp),
                                    horizontalArrangement = Arrangement.spacedBy(ToggleButtonDefaults.IconSpacing),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        imageVector = MoviesIcons.Favorite,
                                        contentDescription = stringResource(MoviesStrings.main_nav_fave),
                                        modifier = Modifier.size(IconButtonDefaults.smallIconSize),
                                        tint = if (currentDestination == FaveDestination) navigationRailItemColors.selectedIconColor else navigationRailItemColors.unselectedIconColor
                                    )

                                    Text(
                                        text = stringResource(MoviesStrings.main_nav_fave),
                                        maxLines = 1,
                                        softWrap = false,
                                        overflow = TextOverflow.Clip,
                                        style = MaterialTheme.typography.titleSmallEmphasized.copy(
                                            color = if (currentDestination == FaveDestination) navigationRailItemColors.selectedTextColor else navigationRailItemColors.unselectedTextColor,
                                            letterSpacing = .4.sp
                                        )
                                    )
                                }
                            }

                            Row(
                                modifier = modifierDisplayCutoutWindowInsets
                                    .fillMaxWidth()
                                    .height(56.dp)
                                    .clip(RoundedCornerShape(16.dp))
                                    .background(if (currentDestination == SettingsDestination) toggleButtonColors.checkedContainerColor else Color.Transparent)
                                    .clickable { dispatch(MainTabsIntent.SettingsClick) }
                                    .padding(horizontal = 16.dp),
                                horizontalArrangement = Arrangement.spacedBy(ToggleButtonDefaults.IconSpacing),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = MoviesIcons.Settings,
                                    contentDescription = stringResource(MoviesStrings.main_nav_settings),
                                    modifier = Modifier.size(IconButtonDefaults.smallIconSize),
                                    tint = if (currentDestination == SettingsDestination) navigationRailItemColors.selectedIconColor else navigationRailItemColors.unselectedIconColor
                                )

                                Text(
                                    text = stringResource(MoviesStrings.main_nav_settings),
                                    maxLines = 1,
                                    softWrap = false,
                                    overflow = TextOverflow.Clip,
                                    style = MaterialTheme.typography.titleSmallEmphasized.copy(
                                        color = if (currentDestination == SettingsDestination) navigationRailItemColors.selectedTextColor else navigationRailItemColors.unselectedTextColor,
                                        letterSpacing = .4.sp
                                    )
                                )
                            }
                        }

                        Spacer(
                            modifier = Modifier.weight(1F)
                        )

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(56.dp)
                                .clip(RoundedCornerShape(16.dp))
                                .clickable { dispatch(if (state.isAuthorized) MainTabsIntent.AccountClick else MainTabsIntent.AuthClick) }
                                .padding(horizontal = 16.dp),
                            horizontalArrangement = Arrangement.spacedBy(ToggleButtonDefaults.IconSpacing),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            when {
                                state.isAuthorized -> {
                                    AccountAvatar(
                                        account = state.accountPojo,
                                        fontSize = if (state.accountPojo.letters.length == 1) 16.sp else 13.sp,
                                        modifier = Modifier.size(IconButtonDefaults.largeIconSize)
                                    )
                                }
                                else -> {
                                    Icon(
                                        imageVector = MoviesIcons.AccountCircle,
                                        contentDescription = stringResource(MoviesContentDescription.AccountIcon),
                                        modifier = Modifier.size(IconButtonDefaults.smallIconSize),
                                        tint = navigationRailItemColors.unselectedIconColor
                                    )
                                }
                            }

                            Text(
                                text = stringResource(if (state.isAuthorized) MoviesStrings.account_title else MoviesStrings.login),
                                maxLines = 1,
                                softWrap = false,
                                overflow = TextOverflow.Clip,
                                style = MaterialTheme.typography.titleSmallEmphasized.copy(
                                    color = navigationRailItemColors.unselectedTextColor,
                                    letterSpacing = .4.sp
                                )
                            )
                        }
                    }
                }
            }
        },
        navigationSuiteType = navigationSuiteType,
        state = navigationSuiteScaffoldState
    ) {
        NavDisplay(
            backStack = backStack,
            modifier = Modifier.fillMaxSize(),
            entryDecorators = listOf(
                rememberSaveableStateHolderNavEntryDecorator(),
                rememberViewModelStoreNavEntryDecorator()
            ),
            transitionSpec = fadeTransitionSpec(),
            popTransitionSpec = fadeTransitionSpec(),
            predictivePopTransitionSpec = fadePredictiveTransitionSpec(),
            entryProvider = entryProvider {
                entry<FeedDestination> {
                    FeedScreen(
                        initialSearchActive = openSearchOnFeedStart,
                        onSearchActiveChange = {
                            isFeedSearchActive = it
                            if (openSearchOnFeedStart) {
                                openSearchOnFeedStart = false
                            }
                        }
                    )
                }
                entry<FaveDestination> { FaveScreen() }
                entry<SettingsDestination> { SettingsScreen() }
            }
        )
    }

    LaunchedEffect(shouldShowNavigation) {
        when {
            shouldShowNavigation -> navigationSuiteScaffoldState.show()
            else -> navigationSuiteScaffoldState.hide()
        }
    }
}
