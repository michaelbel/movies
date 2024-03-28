@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalResourceApi::class)

package org.michaelbel.movies.settings.ui

import androidx.compose.foundation.background
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
import androidx.compose.material3.SnackbarDuration
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
import org.michaelbel.movies.common.version.AppVersionData
import org.michaelbel.movies.settings.ktx.iconSnackbarTextRes
import org.michaelbel.movies.settings.ktx.stringText
import org.michaelbel.movies.settings.ui.common.SettingAppIcon
import org.michaelbel.movies.settings.ui.common.SettingItem
import org.michaelbel.movies.settings.ui.common.SettingSwitchItem
import org.michaelbel.movies.settings.ui.common.SettingsDialog
import org.michaelbel.movies.ui.appicon.IconAlias
import org.michaelbel.movies.ui.icons.MoviesIcons
import org.michaelbel.movies.ui.ktx.clickableWithoutRipple
import org.michaelbel.movies.ui.strings.MoviesStrings

@Composable
internal fun SettingsScreenContent(
    onBackClick: () -> Unit,
    currentLanguage: AppLanguage,
    onLanguageSelect: (AppLanguage) -> Unit,
    currentTheme: AppTheme,
    onThemeSelect: (AppTheme) -> Unit,
    currentFeedView: FeedView,
    onFeedViewSelect: (FeedView) -> Unit,
    currentMovieList: MovieList,
    onMovieListSelect: (MovieList) -> Unit,
    isGrammaticalGenderFeatureEnabled: Boolean,
    isDynamicColorsFeatureEnabled: Boolean,
    dynamicColors: Boolean,
    onSetDynamicColors: (Boolean) -> Unit,
    isPostNotificationsFeatureEnabled: Boolean,
    isTileFeatureEnabled: Boolean,
    isBiometricFeatureEnabled: Boolean,
    isBiometricEnabled: Boolean,
    onSetBiometricEnabled: (Boolean) -> Unit,
    isReviewFeatureEnabled: Boolean,
    isUpdateFeatureEnabled: Boolean,
    onRequestReview: () -> Unit,
    onRequestUpdate: () -> Unit,
    appVersionData: AppVersionData,
    onNavigateToAppNotificationSettings: () -> Unit,
    versionName: String,
    versionCode: Long,
    isDebug: Boolean,
    windowInsets: WindowInsets,
    onNavigateToUrl: (String) -> Unit,
    onRequestAddTileService: ((String) -> Unit) -> Unit,
    onPinAppWidget: () -> Unit,
    areNotificationsEnabled: Boolean,
    onNotificationEnabledClick: () -> Unit,
    currentGrammaticalGender: GrammaticalGender,
    onGenderSelect: (GrammaticalGender) -> Unit,
    isRedIconEnabled: Boolean,
    isPurpleIconEnabled: Boolean,
    isBrownIconEnabled: Boolean,
    isAmoledIconEnabled: Boolean,
    onSetIcon: (IconAlias) -> Unit,
    snackbarHostState: SnackbarHostState,
    modifier: Modifier = Modifier
) {
    val scope = rememberCoroutineScope()
    val topAppBarScrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(
        state = rememberTopAppBarState(),
        canScroll = { true }
    )
    val lazyListState = rememberLazyListState()

    val onShowSnackbar: (String) -> Unit = { message ->
        scope.launch {
            snackbarHostState.showSnackbar(
                message = message,
                duration = SnackbarDuration.Short
            )
        }
    }

    val onScrollToTop: () -> Unit = {
        scope.launch {
            lazyListState.animateScrollToItem(0)
        }
    }

    Scaffold(
        modifier = modifier.nestedScroll(topAppBarScrollBehavior.nestedScrollConnection),
        topBar = {
            SettingsToolbar(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickableWithoutRipple { onScrollToTop() },
                topAppBarScrollBehavior = topAppBarScrollBehavior,
                onNavigationIconClick = onBackClick
            )
        },
        bottomBar = {
            SettingsVersionBox(
                appVersionData = appVersionData,
                versionName = versionName,
                versionCode = versionCode,
                isDebug = isDebug,
                modifier = Modifier
                    .navigationBarsPadding()
                    .windowInsetsPadding(windowInsets)
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.primaryContainer)
            )
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
            item {
                var languageDialog by remember { mutableStateOf(false) }

                if (languageDialog) {
                    SettingsDialog(
                        icon = MoviesIcons.Language,
                        title = stringResource(MoviesStrings.settings_language),
                        items = AppLanguage.VALUES,
                        currentItem = currentLanguage,
                        onItemSelect = onLanguageSelect,
                        onDismissRequest = { languageDialog = false }
                    )
                }

                SettingItem(
                    title = stringResource(MoviesStrings.settings_language),
                    description = currentLanguage.stringText,
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
            item {
                var themeDialog by remember { mutableStateOf(false) }

                if (themeDialog) {
                    SettingsDialog(
                        icon = MoviesIcons.ThemeLightDark,
                        title = stringResource(MoviesStrings.settings_theme),
                        items = AppTheme.VALUES,
                        currentItem = currentTheme,
                        onItemSelect = onThemeSelect,
                        onDismissRequest = { themeDialog = false }
                    )
                }

                SettingItem(
                    title = stringResource(MoviesStrings.settings_theme),
                    description = currentTheme.stringText,
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
            item {
                var appearanceDialog by remember { mutableStateOf(false) }

                if (appearanceDialog) {
                    SettingsDialog(
                        icon = MoviesIcons.GridView,
                        title = stringResource(MoviesStrings.settings_appearance),
                        items = FeedView.VALUES,
                        currentItem = currentFeedView,
                        onItemSelect = onFeedViewSelect,
                        onDismissRequest = { appearanceDialog = false }
                    )
                }

                SettingItem(
                    title = stringResource(MoviesStrings.settings_appearance),
                    description = currentFeedView.stringText,
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
            item {
                var movieListDialog by remember { mutableStateOf(false) }

                if (movieListDialog) {
                    SettingsDialog(
                        icon = MoviesIcons.LocalMovies,
                        title = stringResource(MoviesStrings.settings_movie_list),
                        items = MovieList.VALUES,
                        currentItem = currentMovieList,
                        onItemSelect = onMovieListSelect,
                        onDismissRequest = { movieListDialog = false }
                    )
                }

                SettingItem(
                    title = stringResource(MoviesStrings.settings_movie_list),
                    description = currentMovieList.stringText,
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
            if (isGrammaticalGenderFeatureEnabled) {
                item {
                    var genderDialog by remember { mutableStateOf(false) }
                    if (genderDialog) {
                        SettingsDialog(
                            icon = MoviesIcons.Cat,
                            title = stringResource(MoviesStrings.settings_gender),
                            items = GrammaticalGender.VALUES,
                            currentItem = currentGrammaticalGender,
                            onItemSelect = { item -> onGenderSelect(item) },
                            onDismissRequest = { genderDialog = false }
                        )
                    }

                    SettingItem(
                        title = stringResource(MoviesStrings.settings_gender),
                        description = currentGrammaticalGender.stringText,
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
            if (isDynamicColorsFeatureEnabled) {
                item {
                    SettingSwitchItem(
                        title = stringResource(MoviesStrings.settings_dynamic_colors),
                        description = stringResource(MoviesStrings.settings_dynamic_colors_description),
                        icon = MoviesIcons.Palette,
                        checked = dynamicColors,
                        onClick = { onSetDynamicColors(!dynamicColors) }
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
            if (isPostNotificationsFeatureEnabled) {
                item {
                    SettingSwitchItem(
                        title = stringResource(MoviesStrings.settings_post_notifications),
                        description = stringResource(if (areNotificationsEnabled) MoviesStrings.settings_post_notifications_granted else MoviesStrings.settings_post_notifications_denied),
                        icon = MoviesIcons.Notifications,
                        checked = areNotificationsEnabled,
                        onClick = onNotificationEnabledClick
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
            if (isBiometricFeatureEnabled) {
                item {
                    SettingSwitchItem(
                        title = stringResource(MoviesStrings.settings_lock_app),
                        description = stringResource(if (isBiometricEnabled) MoviesStrings.settings_biometric_added else MoviesStrings.settings_biometric_not_added),
                        icon = MoviesIcons.Fingerprint,
                        checked = isBiometricEnabled,
                        onClick = { onSetBiometricEnabled(!isBiometricEnabled) }
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
            item {
                SettingItem(
                    title = stringResource(MoviesStrings.settings_app_widget),
                    description = stringResource(MoviesStrings.settings_app_widget_description, MoviesStrings.appwidget_description),
                    icon = MoviesIcons.Widgets,
                    onClick = onPinAppWidget
                )
            }
            if (isTileFeatureEnabled) {
                item {
                    HorizontalDivider(
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp),
                        thickness = .1.dp,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }
                item {
                    SettingItem(
                        title = stringResource(MoviesStrings.settings_tile),
                        description = stringResource(MoviesStrings.settings_tile_description),
                        icon = MoviesIcons.ViewAgenda,
                        onClick = {
                            onRequestAddTileService { message -> onShowSnackbar(message) }
                        }
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
                    val messageRed = stringResource(MoviesStrings.settings_app_launcher_icon_changed_to, stringResource(IconAlias.Red.iconSnackbarTextRes))
                    val messagePurple = stringResource(MoviesStrings.settings_app_launcher_icon_changed_to, stringResource(IconAlias.Purple.iconSnackbarTextRes))
                    val messageBrown = stringResource(MoviesStrings.settings_app_launcher_icon_changed_to, stringResource(IconAlias.Brown.iconSnackbarTextRes))
                    val messageAmoled = stringResource(MoviesStrings.settings_app_launcher_icon_changed_to, stringResource(IconAlias.Amoled.iconSnackbarTextRes))

                    SettingAppIcon(
                        iconAlias = IconAlias.Red,
                        isEnabled = isRedIconEnabled,
                        onClick = { icon ->
                            onShowSnackbar(messageRed)
                            onSetIcon(icon)
                        }
                    )

                    SettingAppIcon(
                        iconAlias = IconAlias.Purple,
                        isEnabled = isPurpleIconEnabled,
                        onClick = { icon ->
                            onShowSnackbar(messagePurple)
                            onSetIcon(icon)
                        }
                    )

                    SettingAppIcon(
                        iconAlias = IconAlias.Brown,
                        isEnabled = isBrownIconEnabled,
                        onClick = { icon ->
                            onShowSnackbar(messageBrown)
                            onSetIcon(icon)
                        }
                    )

                    SettingAppIcon(
                        iconAlias = IconAlias.Amoled,
                        isEnabled = isAmoledIconEnabled,
                        onClick = { icon ->
                            onShowSnackbar(messageAmoled)
                            onSetIcon(icon)
                        }
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
            item {
                SettingItem(
                    title = stringResource(MoviesStrings.settings_github),
                    description = stringResource(MoviesStrings.settings_github_description),
                    icon = MoviesIcons.Github,
                    onClick = { onNavigateToUrl(MOVIES_GITHUB_URL) }
                )
            }
            if (isReviewFeatureEnabled) {
                item {
                    HorizontalDivider(
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp),
                        thickness = .1.dp,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }
                item {
                    SettingItem(
                        title = stringResource(MoviesStrings.settings_review),
                        description = stringResource(MoviesStrings.settings_review_description),
                        icon = MoviesIcons.GooglePlay,
                        onClick = onRequestReview
                    )
                }
            }
            if (isUpdateFeatureEnabled) {
                item {
                    HorizontalDivider(
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp),
                        thickness = .1.dp,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }
                item {
                    SettingItem(
                        title = stringResource(MoviesStrings.settings_update),
                        description = stringResource(MoviesStrings.settings_update_description),
                        icon = MoviesIcons.SystemUpdate,
                        onClick = onRequestUpdate
                    )
                }
            }
        }
    }
}