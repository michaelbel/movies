@file:OptIn(ExperimentalMaterial3ExpressiveApi::class)

package org.michaelbel.movies.main.tabs

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.NavigationRailItemDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.ToggleButtonDefaults
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffoldLayout
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteType
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
import org.koin.compose.koinInject
import org.michaelbel.movies.fave.FaveScreen
import org.michaelbel.movies.fave.event.FaveEvent
import org.michaelbel.movies.fave.event.FaveEventManager
import org.michaelbel.movies.feed.FeedScreen
import org.michaelbel.movies.interactor.UiInteractor
import org.michaelbel.movies.main.MainWebDestination
import org.michaelbel.movies.main.mainWebWindowWidth
import org.michaelbel.movies.main.tabs.event.MainWebTabsEventManager
import org.michaelbel.movies.persistence.database.entity.pojo.AccountPojo
import org.michaelbel.movies.persistence.database.ktx.isNotEmpty
import org.michaelbel.movies.persistence.database.ktx.letters
import org.michaelbel.movies.persistence.database.typealiases.MovieId
import org.michaelbel.movies.persistence.database.typealiases.PagingKey
import org.michaelbel.movies.settings.SettingsScreen
import org.michaelbel.movies.settings.event.SettingsEvent
import org.michaelbel.movies.settings.event.SettingsEventManager
import org.michaelbel.movies.ui.compose.AccountAvatar
import org.michaelbel.movies.ui.icons.MoviesIcons

@Composable
fun MainWebTabsScreen(
    currentDestination: MainWebDestination,
    onDestinationChange: (MainWebDestination) -> Unit,
    onMovieClick: (PagingKey, MovieId) -> Unit,
    accountPojo: AccountPojo,
    onAccountClick: () -> Unit,
    uiInteractor: UiInteractor = koinInject()
) {
    val isAuthorized = accountPojo.isNotEmpty

    val scope = rememberCoroutineScope()

    val onTabClick: (MainWebDestination) -> Unit = { destination ->
        when (destination) {
            currentDestination -> {
                MainWebTabsEventManager.reselect(destination)
                if (destination == MainWebDestination.Fave) {
                    scope.launch {
                        FaveEventManager.push(FaveEvent.ReselectFave)
                    }
                }
                if (destination == MainWebDestination.Settings) {
                    scope.launch {
                        SettingsEventManager.push(SettingsEvent.ReselectSettings)
                    }
                }
            }
            else -> {
                onDestinationChange(destination)
            }
        }
    }
    val toggleButtonColors = ToggleButtonDefaults.colors()
    val navigationRailItemColors = NavigationRailItemDefaults.colors(
        selectedIconColor = toggleButtonColors.checkedContentColor,
        selectedTextColor = toggleButtonColors.checkedContentColor,
        indicatorColor = toggleButtonColors.checkedContainerColor,
        unselectedIconColor = toggleButtonColors.contentColor,
        unselectedTextColor = toggleButtonColors.contentColor,
        disabledIconColor = toggleButtonColors.disabledContentColor,
        disabledTextColor = toggleButtonColors.disabledContentColor
    )

    val windowWidth = mainWebWindowWidth

    when {
        windowWidth >= 1200.dp -> {
            NavigationSuiteScaffoldLayout(
                navigationSuite = {
                    Surface(
                        modifier = Modifier
                            .fillMaxHeight()
                            .width(192.dp),
                        color = MaterialTheme.colorScheme.primaryContainer
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(start = 12.dp, end = 12.dp, bottom = 16.dp)
                        ) {
                            Column(
                                modifier = Modifier
                                    .weight(1F)
                                    .fillMaxWidth(),
                                verticalArrangement = Arrangement.spacedBy(
                                    space = 8.dp,
                                    alignment = Alignment.CenterVertically
                                )
                            ) {
                                Surface(
                                    onClick = { onTabClick(MainWebDestination.Feed) },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(56.dp),
                                    shape = RoundedCornerShape(16.dp),
                                    color = when {
                                        currentDestination == MainWebDestination.Feed -> toggleButtonColors.checkedContainerColor
                                        else -> Color.Transparent
                                    },
                                    contentColor = when {
                                        currentDestination == MainWebDestination.Feed -> toggleButtonColors.checkedContentColor
                                        else -> toggleButtonColors.contentColor
                                    }
                                ) {
                                    Row(
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .padding(horizontal = 16.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Icon(
                                            imageVector = MoviesIcons.GridView,
                                            contentDescription = null,
                                            modifier = Modifier.size(IconButtonDefaults.smallIconSize),
                                            tint = when {
                                                currentDestination == MainWebDestination.Feed -> navigationRailItemColors.selectedIconColor
                                                else -> navigationRailItemColors.unselectedIconColor
                                            }
                                        )
    
                                        Text(
                                            text = "Feed",
                                            modifier = Modifier.padding(start = ToggleButtonDefaults.IconSpacing),
                                            maxLines = 1,
                                            softWrap = false,
                                            overflow = TextOverflow.Clip,
                                            style = MaterialTheme.typography.titleSmallEmphasized.copy(
                                                color = when {
                                                    currentDestination == MainWebDestination.Feed -> navigationRailItemColors.selectedTextColor
                                                    else -> navigationRailItemColors.unselectedTextColor
                                                },
                                                letterSpacing = .4.sp
                                            )
                                        )
                                    }
                                }
    
                                if (uiInteractor.isFaveFeatureEnabled) {
                                    Surface(
                                        onClick = { onTabClick(MainWebDestination.Fave) },
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(56.dp),
                                        shape = RoundedCornerShape(16.dp),
                                        color = when {
                                            currentDestination == MainWebDestination.Fave -> toggleButtonColors.checkedContainerColor
                                            else -> Color.Transparent
                                        },
                                        contentColor = when {
                                            currentDestination == MainWebDestination.Fave -> toggleButtonColors.checkedContentColor
                                            else -> toggleButtonColors.contentColor
                                        }
                                    ) {
                                        Row(
                                            modifier = Modifier
                                                .fillMaxSize()
                                                .padding(horizontal = 16.dp),
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Icon(
                                                imageVector = MoviesIcons.Favorite,
                                                contentDescription = null,
                                                modifier = Modifier.size(IconButtonDefaults.smallIconSize),
                                                tint = when {
                                                    currentDestination == MainWebDestination.Fave -> navigationRailItemColors.selectedIconColor
                                                    else -> navigationRailItemColors.unselectedIconColor
                                                }
                                            )

                                            Text(
                                                text = "Fave",
                                                modifier = Modifier.padding(start = ToggleButtonDefaults.IconSpacing),
                                                maxLines = 1,
                                                softWrap = false,
                                                overflow = TextOverflow.Clip,
                                                style = MaterialTheme.typography.titleSmallEmphasized.copy(
                                                    color = when {
                                                        currentDestination == MainWebDestination.Fave -> navigationRailItemColors.selectedTextColor
                                                        else -> navigationRailItemColors.unselectedTextColor
                                                    },
                                                    letterSpacing = .4.sp
                                                )
                                            )
                                        }
                                    }
                                }

Surface(
                                    onClick = { onTabClick(MainWebDestination.Settings) },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(56.dp),
                                    shape = RoundedCornerShape(16.dp),
                                    color = when {
                                        currentDestination == MainWebDestination.Settings -> toggleButtonColors.checkedContainerColor
                                        else -> Color.Transparent
                                    },
                                    contentColor = when {
                                        currentDestination == MainWebDestination.Settings -> toggleButtonColors.checkedContentColor
                                        else -> toggleButtonColors.contentColor
                                    }
                                ) {
                                    Row(
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .padding(horizontal = 16.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Icon(
                                            imageVector = MoviesIcons.Settings,
                                            contentDescription = null,
                                            modifier = Modifier.size(IconButtonDefaults.smallIconSize),
                                            tint = when {
                                                currentDestination == MainWebDestination.Settings -> navigationRailItemColors.selectedIconColor
                                                else -> navigationRailItemColors.unselectedIconColor
                                            }
                                        )
    
                                        Text(
                                            text = "Settings",
                                            modifier = Modifier.padding(start = ToggleButtonDefaults.IconSpacing),
                                            maxLines = 1,
                                            softWrap = false,
                                            overflow = TextOverflow.Clip,
                                            style = MaterialTheme.typography.titleSmallEmphasized.copy(
                                                color = when {
                                                    currentDestination == MainWebDestination.Settings -> navigationRailItemColors.selectedTextColor
                                                    else -> navigationRailItemColors.unselectedTextColor
                                                },
                                                letterSpacing = .4.sp
                                            )
                                        )
                                    }
                                }
                            }

                            Surface(
                                onClick = onAccountClick,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(56.dp),
                                shape = RoundedCornerShape(16.dp),
                                color = Color.Transparent,
                                contentColor = toggleButtonColors.contentColor
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(horizontal = 16.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    when {
                                        isAuthorized -> {
                                            AccountAvatar(
                                                account = accountPojo,
                                                fontSize = if (accountPojo.letters.length == 1) 16.sp else 13.sp,
                                                modifier = Modifier.size(IconButtonDefaults.largeIconSize)
                                            )
                                        }
                                        else -> {
                                            Icon(
                                                imageVector = MoviesIcons.AccountCircle,
                                                contentDescription = "Account Icon",
                                                modifier = Modifier.size(IconButtonDefaults.smallIconSize),
                                                tint = navigationRailItemColors.unselectedIconColor
                                            )
                                        }
                                    }

                                    Text(
                                        text = if (isAuthorized) "Account" else "Login",
                                        modifier = Modifier.padding(start = ToggleButtonDefaults.IconSpacing),
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
                navigationSuiteType = NavigationSuiteType.WideNavigationRailExpanded
            ) {
                when (currentDestination) {
                    MainWebDestination.Feed -> FeedScreen(onMovieClick = onMovieClick)
                    MainWebDestination.Fave -> FaveScreen()
                    MainWebDestination.Settings -> SettingsScreen()
                    is MainWebDestination.Details -> Unit
                }
            }
        }
        windowWidth >= 840.dp -> {
            NavigationSuiteScaffoldLayout(
                navigationSuite = {
                    Column(
                        modifier = Modifier
                            .fillMaxHeight()
                            .width(80.dp)
                            .background(MaterialTheme.colorScheme.primaryContainer)
                            .padding(bottom = 16.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .weight(1F)
                                .fillMaxWidth(),
                            verticalArrangement = Arrangement.spacedBy(4.dp, Alignment.CenterVertically),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            NavigationRailItem(
                                selected = currentDestination == MainWebDestination.Feed,
                                onClick = { onTabClick(MainWebDestination.Feed) },
                                icon = {
                                    Icon(
                                        imageVector = MoviesIcons.GridView,
                                        contentDescription = null
                                    )
                                },
                                colors = navigationRailItemColors
                            )

                            if (uiInteractor.isFaveFeatureEnabled) {
                                NavigationRailItem(
                                    selected = currentDestination == MainWebDestination.Fave,
                                    onClick = { onTabClick(MainWebDestination.Fave) },
                                    icon = {
                                        Icon(
                                            imageVector = MoviesIcons.Favorite,
                                            contentDescription = null
                                        )
                                    },
                                    colors = navigationRailItemColors
                                )
                            }

                            NavigationRailItem(
                                selected = currentDestination == MainWebDestination.Settings,
                                onClick = { onTabClick(MainWebDestination.Settings) },
                                icon = {
                                    Icon(
                                        imageVector = MoviesIcons.Settings,
                                        contentDescription = null
                                    )
                                },
                                colors = navigationRailItemColors
                            )
                        }

                        NavigationRailItem(
                            selected = false,
                            onClick = onAccountClick,
                            icon = {
                                when {
                                    isAuthorized -> {
                                        AccountAvatar(
                                            account = accountPojo,
                                            fontSize = if (accountPojo.letters.length == 1) 16.sp else 13.sp,
                                            modifier = Modifier.size(IconButtonDefaults.largeIconSize)
                                        )
                                    }
                                    else -> {
                                        Icon(
                                            imageVector = MoviesIcons.AccountCircle,
                                            contentDescription = "Account Icon"
                                        )
                                    }
                                }
                            },
                            colors = navigationRailItemColors
                        )
                    }
                },
                navigationSuiteType = NavigationSuiteType.WideNavigationRailCollapsed
            ) {
                when (currentDestination) {
                    is MainWebDestination.Feed -> {
                        FeedScreen(
                            onMovieClick = onMovieClick
                        )
                    }
                    is MainWebDestination.Fave -> {
                        FaveScreen()
                    }
                    is MainWebDestination.Settings -> SettingsScreen()
                    is MainWebDestination.Details -> Unit
                }
            }
        }
        else -> {
            NavigationSuiteScaffold(
                modifier = Modifier.fillMaxSize(),
                layoutType = if (windowWidth >= 600.dp) NavigationSuiteType.ShortNavigationBarMedium else NavigationSuiteType.ShortNavigationBarCompact,
                navigationSuiteItems = {
                    item(
                        selected = currentDestination == MainWebDestination.Feed,
                        onClick = { onTabClick(MainWebDestination.Feed) },
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
                    if (uiInteractor.isFaveFeatureEnabled) {
                        item(
                            selected = currentDestination == MainWebDestination.Fave,
                            onClick = { onTabClick(MainWebDestination.Fave) },
                            icon = {
                                Icon(
                                    imageVector = MoviesIcons.Favorite,
                                    contentDescription = null
                                )
                            },
                            label = {
                                Text(
                                    text = "Fave"
                                )
                            }
                        )
                    }
                    item(
                        selected = currentDestination == MainWebDestination.Settings,
                        onClick = { onTabClick(MainWebDestination.Settings) },
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
                    item(
                        selected = false,
                        onClick = onAccountClick,
                        icon = {
                            when {
                                isAuthorized -> {
                                    AccountAvatar(
                                        account = accountPojo,
                                        fontSize = if (accountPojo.letters.length == 1) 12.sp else 10.sp,
                                        modifier = Modifier.size(28.dp)
                                    )
                                }
                                else -> {
                                    Icon(
                                        imageVector = MoviesIcons.AccountCircle,
                                        contentDescription = "Account Icon"
                                    )
                                }
                            }
                        },
                        label = {
                            Text(
                                text = if (isAuthorized) "Account" else "Login"
                            )
                        }
                    )
                }
            ) {
                when (currentDestination) {
                    MainWebDestination.Feed -> FeedScreen(onMovieClick = onMovieClick)
                    MainWebDestination.Fave -> FaveScreen()
                    MainWebDestination.Settings -> SettingsScreen()
                    is MainWebDestination.Details -> Unit
                }
            }
        }
    }
}
