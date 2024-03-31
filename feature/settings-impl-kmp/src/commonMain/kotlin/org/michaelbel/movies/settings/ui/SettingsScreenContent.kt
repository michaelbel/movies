@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalResourceApi::class)

package org.michaelbel.movies.settings.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.stringResource
import org.michaelbel.movies.common.MOVIES_GITHUB_URL
import org.michaelbel.movies.common.appearance.FeedView
import org.michaelbel.movies.common.gender.GrammaticalGender
import org.michaelbel.movies.common.list.MovieList
import org.michaelbel.movies.common.localization.model.AppLanguage
import org.michaelbel.movies.common.theme.AppTheme
import org.michaelbel.movies.settings.ktx.SettingsGenderText
import org.michaelbel.movies.settings.ktx.stringText
import org.michaelbel.movies.settings.model.SettingsData
import org.michaelbel.movies.settings.ui.common.SettingAppIcon
import org.michaelbel.movies.settings.ui.common.SettingItem
import org.michaelbel.movies.settings.ui.common.SettingSwitchItem
import org.michaelbel.movies.settings.ui.common.SettingsDialog
import org.michaelbel.movies.ui.appicon.IconAlias
import org.michaelbel.movies.ui.icons.MoviesIcons
import org.michaelbel.movies.ui.strings.MoviesStrings

@Composable
internal fun SettingsScreenContent(
    settingsData: SettingsData,
    windowInsets: WindowInsets,
    snackbarHostState: SnackbarHostState,
    modifier: Modifier = Modifier
) {
    val scope = rememberCoroutineScope()
    val topAppBarScrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(
        state = rememberTopAppBarState(),
        canScroll = { true }
    )
    val lazyListState = rememberLazyListState()
    val onScrollToTop: () -> Unit = {
        scope.launch { lazyListState.animateScrollToItem(0) }
    }

    Scaffold(
        modifier = modifier
            .fillMaxWidth()
            .nestedScroll(topAppBarScrollBehavior.nestedScrollConnection),
        topBar = {
            SettingsToolbar(
                topAppBarScrollBehavior = topAppBarScrollBehavior,
                onNavigationIconClick = settingsData.onBackClick,
                onClick = onScrollToTop
            )
        },
        bottomBar = {
            if (settingsData.aboutData.isFeatureEnabled) {
                SettingsVersionBox(
                    aboutData = settingsData.aboutData,
                    modifier = Modifier
                        .navigationBarsPadding()
                        .windowInsetsPadding(windowInsets)
                )
            }
        },
        snackbarHost = {
            SnackbarHost(
                hostState = snackbarHostState
            )
        },
        containerColor = MaterialTheme.colorScheme.primaryContainer
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .navigationBarsPadding()
                .windowInsetsPadding(windowInsets),
            state = lazyListState,
            contentPadding = innerPadding
        ) {
            if (settingsData.languageData.isFeatureEnabled) {
                item {
                    var languageDialog by remember { mutableStateOf(false) }

                    if (languageDialog) {
                        SettingsDialog(
                            icon = MoviesIcons.Language,
                            title = stringResource(MoviesStrings.settings_language),
                            items = AppLanguage.VALUES,
                            currentItem = settingsData.languageData.current,
                            onItemSelect = settingsData.languageData.onSelect,
                            onDismissRequest = { languageDialog = false }
                        )
                    }

                    SettingItem(
                        title = stringResource(MoviesStrings.settings_language),
                        description = settingsData.languageData.current.stringText,
                        icon = MoviesIcons.Language,
                        onClick = { languageDialog = true }
                    )
                }
                item {
                    HorizontalDivider(
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp),
                        thickness = .1.dp,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }
            }
            if (settingsData.themeData.isFeatureEnabled) {
                item {
                    var themeDialog by remember { mutableStateOf(false) }

                    if (themeDialog) {
                        SettingsDialog(
                            icon = MoviesIcons.ThemeLightDark,
                            title = stringResource(MoviesStrings.settings_theme),
                            items = AppTheme.VALUES,
                            currentItem = settingsData.themeData.current,
                            onItemSelect = settingsData.themeData.onSelect,
                            onDismissRequest = { themeDialog = false }
                        )
                    }

                    SettingItem(
                        title = stringResource(MoviesStrings.settings_theme),
                        description = settingsData.themeData.current.stringText,
                        icon = MoviesIcons.ThemeLightDark,
                        onClick = { themeDialog = true }
                    )
                }
                item {
                    HorizontalDivider(
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp),
                        thickness = .1.dp,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }
            }
            if (settingsData.feedViewData.isFeatureEnabled) {
                item {
                    var appearanceDialog by remember { mutableStateOf(false) }

                    if (appearanceDialog) {
                        SettingsDialog(
                            icon = MoviesIcons.GridView,
                            title = stringResource(MoviesStrings.settings_appearance),
                            items = FeedView.VALUES,
                            currentItem = settingsData.feedViewData.current,
                            onItemSelect = settingsData.feedViewData.onSelect,
                            onDismissRequest = { appearanceDialog = false }
                        )
                    }

                    SettingItem(
                        title = stringResource(MoviesStrings.settings_appearance),
                        description = settingsData.feedViewData.current.stringText,
                        icon = MoviesIcons.GridView,
                        onClick = { appearanceDialog = true }
                    )
                }
                item {
                    HorizontalDivider(
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp),
                        thickness = .1.dp,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }
            }
            if (settingsData.movieListData.isFeatureEnabled) {
                item {
                    var movieListDialog by remember { mutableStateOf(false) }

                    if (movieListDialog) {
                        SettingsDialog(
                            icon = MoviesIcons.LocalMovies,
                            title = stringResource(MoviesStrings.settings_movie_list),
                            items = MovieList.VALUES,
                            currentItem = settingsData.movieListData.current,
                            onItemSelect = settingsData.movieListData.onSelect,
                            onDismissRequest = { movieListDialog = false }
                        )
                    }

                    SettingItem(
                        title = stringResource(MoviesStrings.settings_movie_list),
                        description = settingsData.movieListData.current.stringText,
                        icon = MoviesIcons.LocalMovies,
                        onClick = { movieListDialog = true }
                    )
                }
                item {
                    HorizontalDivider(
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp),
                        thickness = .1.dp,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }
            }
            if (settingsData.genderData.isFeatureEnabled) {
                item {
                    var genderDialog by remember { mutableStateOf(false) }
                    if (genderDialog) {
                        SettingsDialog(
                            icon = MoviesIcons.Cat,
                            title = stringResource(MoviesStrings.settings_gender),
                            items = GrammaticalGender.VALUES,
                            currentItem = settingsData.genderData.current,
                            onItemSelect = settingsData.genderData.onSelect,
                            onDismissRequest = { genderDialog = false }
                        )
                    }

                    SettingItem(
                        title = SettingsGenderText,
                        description = settingsData.genderData.current.stringText,
                        icon = MoviesIcons.Cat,
                        onClick = { genderDialog = true }
                    )
                }
                item {
                    HorizontalDivider(
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp),
                        thickness = .1.dp,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }
            }
            if (settingsData.dynamicColorsData.isFeatureEnabled) {
                item {
                    SettingSwitchItem(
                        title = stringResource(MoviesStrings.settings_dynamic_colors),
                        description = stringResource(MoviesStrings.settings_dynamic_colors_description),
                        icon = MoviesIcons.Palette,
                        checked = settingsData.dynamicColorsData.isEnabled,
                        onClick = { settingsData.dynamicColorsData.onChange(!settingsData.dynamicColorsData.isEnabled) }
                    )
                }
                item {
                    HorizontalDivider(
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp),
                        thickness = .1.dp,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }
            }
            if (settingsData.paletteColorsData.isFeatureEnabled) {
                item {
                    Text(
                        text = stringResource(MoviesStrings.settings_palette_colors),
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                        style = MaterialTheme.typography.titleLarge.copy(MaterialTheme.colorScheme.onPrimaryContainer)
                    )
                }
                item {
                    SettingsPaletteColorsBox(
                        isDynamicColorsEnabled = settingsData.paletteColorsData.isDynamicColorsEnabled,
                        paletteKey = settingsData.paletteColorsData.paletteKey,
                        seedColor = settingsData.paletteColorsData.seedColor,
                        onChange = settingsData.paletteColorsData.onChange
                    )
                }
                item {
                    HorizontalDivider(
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp),
                        thickness = .1.dp,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }
            }
            if (settingsData.notificationsData.isFeatureEnabled) {
                item {
                    SettingSwitchItem(
                        title = stringResource(MoviesStrings.settings_post_notifications),
                        description = stringResource(if (settingsData.notificationsData.isEnabled) MoviesStrings.settings_post_notifications_granted else MoviesStrings.settings_post_notifications_denied),
                        icon = MoviesIcons.Notifications,
                        checked = settingsData.notificationsData.isEnabled,
                        onClick = settingsData.notificationsData.onClick
                    )
                }
                item {
                    HorizontalDivider(
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp),
                        thickness = .1.dp,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }
            }
            if (settingsData.biometricData.isFeatureEnabled) {
                item {
                    SettingSwitchItem(
                        title = stringResource(MoviesStrings.settings_lock_app),
                        description = stringResource(if (settingsData.biometricData.isEnabled) MoviesStrings.settings_biometric_added else MoviesStrings.settings_biometric_not_added),
                        icon = MoviesIcons.Fingerprint,
                        checked = settingsData.biometricData.isEnabled,
                        onClick = { settingsData.biometricData.onChange(!settingsData.biometricData.isEnabled) }
                    )
                }
                item {
                    HorizontalDivider(
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp),
                        thickness = .1.dp,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }
            }
            if (settingsData.widgetData.isFeatureEnabled) {
                item {
                    SettingItem(
                        title = stringResource(MoviesStrings.settings_app_widget),
                        description = stringResource(MoviesStrings.settings_app_widget_description, stringResource(MoviesStrings.appwidget_description)),
                        icon = MoviesIcons.Widgets,
                        onClick = settingsData.widgetData.onRequest
                    )
                }
                item {
                    HorizontalDivider(
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp),
                        thickness = .1.dp,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }
            }
            if (settingsData.tileData.isFeatureEnabled) {
                item {
                    SettingItem(
                        title = stringResource(MoviesStrings.settings_tile),
                        description = stringResource(MoviesStrings.settings_tile_description),
                        icon = MoviesIcons.ViewAgenda,
                        onClick = settingsData.tileData.onRequest
                    )
                }
                item {
                    HorizontalDivider(
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp),
                        thickness = .1.dp,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }
            }
            if (settingsData.appIconData.isFeatureEnabled) {
                item {
                    Text(
                        text = stringResource(MoviesStrings.settings_app_launcher_icon),
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                        style = MaterialTheme.typography.titleLarge.copy(MaterialTheme.colorScheme.onPrimaryContainer)
                    )
                }
                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        SettingAppIcon(
                            iconAlias = IconAlias.Red,
                            isEnabled = settingsData.appIconData.current == IconAlias.Red,
                            onClick = settingsData.appIconData.onSelect
                        )

                        SettingAppIcon(
                            iconAlias = IconAlias.Purple,
                            isEnabled = settingsData.appIconData.current == IconAlias.Purple,
                            onClick = settingsData.appIconData.onSelect
                        )

                        SettingAppIcon(
                            iconAlias = IconAlias.Brown,
                            isEnabled = settingsData.appIconData.current == IconAlias.Brown,
                            onClick = settingsData.appIconData.onSelect
                        )

                        SettingAppIcon(
                            iconAlias = IconAlias.Amoled,
                            isEnabled = settingsData.appIconData.current == IconAlias.Amoled,
                            onClick = settingsData.appIconData.onSelect
                        )
                    }
                }
                item {
                    HorizontalDivider(
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp),
                        thickness = .1.dp,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }
            }
            if (settingsData.githubData.isFeatureEnabled) {
                item {
                    SettingItem(
                        title = stringResource(MoviesStrings.settings_github),
                        description = stringResource(MoviesStrings.settings_github_description),
                        icon = MoviesIcons.Github,
                        onClick = { settingsData.githubData.onClick(MOVIES_GITHUB_URL) }
                    )
                }
                item {
                    HorizontalDivider(
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp),
                        thickness = .1.dp,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }
            }
            if (settingsData.reviewAppData.isFeatureEnabled) {
                item {
                    SettingItem(
                        title = stringResource(MoviesStrings.settings_review),
                        description = stringResource(MoviesStrings.settings_review_description),
                        icon = MoviesIcons.GooglePlay,
                        onClick = settingsData.reviewAppData.onRequest
                    )
                }
                item {
                    HorizontalDivider(
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp),
                        thickness = .1.dp,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }
            }
            if (settingsData.updateAppData.isFeatureEnabled) {
                item {
                    SettingItem(
                        title = stringResource(MoviesStrings.settings_update),
                        description = stringResource(MoviesStrings.settings_update_description),
                        icon = MoviesIcons.SystemUpdate,
                        onClick = settingsData.updateAppData.onRequest
                    )
                }
            }
        }
    }
}