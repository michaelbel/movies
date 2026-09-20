@file:OptIn(ExperimentalMaterial3ExpressiveApi::class)

package org.michaelbel.movies.main.tabs.ui

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.NavigationRailItemDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.ToggleButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.jetbrains.compose.resources.stringResource
import org.michaelbel.movies.main.tabs.intent.MainTabsIntent
import org.michaelbel.movies.main.tabs.model.MainTabsModel
import org.michaelbel.movies.ui.icons.MoviesIcons
import org.michaelbel.movies.ui.modifierDisplayCutoutWindowInsets
import org.michaelbel.movies.ui.navigation.AppRoute
import org.michaelbel.movies.ui.navigation.FaveDestination
import org.michaelbel.movies.ui.navigation.FeedDestination
import org.michaelbel.movies.ui.navigation.SettingsDestination
import org.michaelbel.movies.ui.strings.MoviesStrings

@Composable
fun MainTabsNavigationRail(
    state: MainTabsModel,
    currentDestination: AppRoute?,
    feedDestination: FeedDestination,
    dispatch: (MainTabsIntent) -> Unit
) {
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

    NavigationRail(
        modifier = Modifier
            .fillMaxHeight(),
        containerColor = MaterialTheme.colorScheme.primaryContainer,
        windowInsets = WindowInsets(0, 0, 0, 0)
    ) {
        Spacer(
            modifier = Modifier.weight(1F)
        )

        NavigationRailItem(
            selected = currentDestination == feedDestination,
            onClick = { dispatch(MainTabsIntent.FeedClick) },
            icon = {
                Icon(
                    imageVector = MoviesIcons.GridView,
                    contentDescription = stringResource(MoviesStrings.main_nav_feed)
                )
            },
            modifier = modifierDisplayCutoutWindowInsets,
            label = {
                Text(
                    text = stringResource(MoviesStrings.main_nav_feed)
                )
            },
            alwaysShowLabel = true,
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
                modifier = modifierDisplayCutoutWindowInsets,
                label = {
                    Text(
                        text = stringResource(MoviesStrings.main_nav_fave)
                    )
                },
                alwaysShowLabel = true,
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
            modifier = modifierDisplayCutoutWindowInsets,
            label = {
                Text(
                    text = stringResource(MoviesStrings.main_nav_settings)
                )
            },
            alwaysShowLabel = true,
            colors = navigationRailItemColors
        )

        Spacer(
            modifier = Modifier.weight(1F)
        )
    }
}
