@file:OptIn(ExperimentalMaterial3Api::class)

package org.michaelbel.movies.settings.ui

import android.Manifest
import android.app.Activity
import android.app.GrammaticalInflectionManager
import android.app.StatusBarManager
import android.appwidget.AppWidgetManager
import android.content.ComponentName
import android.graphics.drawable.Icon
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
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
import androidx.compose.material3.SnackbarResult
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
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.launch
import org.michaelbel.movies.common.MOVIES_GITHUB_URL
import org.michaelbel.movies.common.appearance.FeedView
import org.michaelbel.movies.common.browser.openUrlAndroid
import org.michaelbel.movies.common.gender.GrammaticalGender
import org.michaelbel.movies.common.ktx.notificationManager
import org.michaelbel.movies.common.list.MovieList
import org.michaelbel.movies.common.localization.model.AppLanguage
import org.michaelbel.movies.common.theme.AppTheme
import org.michaelbel.movies.common.version.AppVersionData
import org.michaelbel.movies.settings.SettingsViewModel
import org.michaelbel.movies.settings.ktx.iconSnackbarTextRes
import org.michaelbel.movies.settings.ktx.stringText
import org.michaelbel.movies.settings.ui.common.SettingAppIcon
import org.michaelbel.movies.settings.ui.common.SettingItem
import org.michaelbel.movies.settings.ui.common.SettingSwitchItem
import org.michaelbel.movies.settings.ui.common.SettingsDialog
import org.michaelbel.movies.settings_impl_kmp.R
import org.michaelbel.movies.ui.appicon.IconAlias
import org.michaelbel.movies.ui.appicon.setIcon
import org.michaelbel.movies.ui.icons.MoviesAndroidIcons
import org.michaelbel.movies.ui.icons.MoviesIcons
import org.michaelbel.movies.ui.ktx.appNotificationSettingsIntent
import org.michaelbel.movies.ui.ktx.clickableWithoutRipple
import org.michaelbel.movies.ui.ktx.displayCutoutWindowInsets
import org.michaelbel.movies.ui.lifecycle.OnResume
import org.michaelbel.movies.ui.tile.MoviesTileService
import org.michaelbel.movies.widget.ktx.pin
import org.michaelbel.movies.ui.R as UiR
import org.michaelbel.movies.widget.R as WidgetR

@Composable
fun SettingsRoute(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SettingsViewModel = hiltViewModel()
) {
    val currentLanguage = AppLanguage.transform(stringResource(UiR.string.language_code))
    val currentTheme by viewModel.currentTheme.collectAsStateWithLifecycle()
    val currentFeedView by viewModel.currentFeedView.collectAsStateWithLifecycle()
    val currentMovieList by viewModel.currentMovieList.collectAsStateWithLifecycle()
    val dynamicColors by viewModel.dynamicColors.collectAsStateWithLifecycle()
    val isBiometricFeatureEnabled by viewModel.isBiometricFeatureEnabled.collectAsStateWithLifecycle()
    val isBiometricEnabled by viewModel.isBiometricEnabled.collectAsStateWithLifecycle()
    val appVersionData by viewModel.appVersionData.collectAsStateWithLifecycle()

    SettingsScreenContent(
        onBackClick = onBackClick,
        currentLanguage = currentLanguage,
        onLanguageSelect = viewModel::selectLanguage,
        currentTheme = currentTheme,
        onThemeSelect = viewModel::selectTheme,
        currentFeedView = currentFeedView,
        onFeedViewSelect = viewModel::selectFeedView,
        currentMovieList = currentMovieList,
        onMovieListSelect = viewModel::selectMovieList,
        isGrammaticalGenderFeatureEnabled = viewModel.isGrammaticalGenderFeatureEnabled,
        isDynamicColorsFeatureEnabled = viewModel.isDynamicColorsFeatureEnabled,
        dynamicColors = dynamicColors,
        onSetDynamicColors = viewModel::setDynamicColors,
        isPostNotificationsFeatureEnabled = viewModel.isPostNotificationsFeatureEnabled,
        isTileFeatureEnabled = viewModel.isTileFeatureEnabled,
        isBiometricFeatureEnabled = isBiometricFeatureEnabled,
        isBiometricEnabled = isBiometricEnabled,
        onSetBiometricEnabled = viewModel::setBiometricEnabled,
        isReviewFeatureEnabled = viewModel.isReviewFeatureEnabled,
        isUpdateFeatureEnabled = viewModel.isUpdateFeatureEnabled && viewModel.isUpdateAvailable,
        onRequestReview = viewModel::requestReview,
        onRequestUpdate = viewModel::requestUpdate,
        appVersionData = appVersionData,
        modifier = modifier
    )
}

@Composable
private fun SettingsScreenContent(
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
    onRequestReview: (Activity) -> Unit,
    onRequestUpdate: (Activity) -> Unit,
    appVersionData: AppVersionData,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    val topAppBarScrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(
        state = rememberTopAppBarState(),
        canScroll = { true }
    )
    val lazyListState = rememberLazyListState()
    val resultContract = rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) {}

    val onShowPermissionSnackbar: () -> Unit = {
        scope.launch {
            val result = snackbarHostState.showSnackbar(
                message = context.getString(R.string.settings_post_notifications_should_request),
                actionLabel = context.getString(R.string.settings_action_go),
                duration = SnackbarDuration.Long
            )
            if (result == SnackbarResult.ActionPerformed) {
                resultContract.launch(context.appNotificationSettingsIntent)
            }
        }
    }

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
                modifier = Modifier
                    .navigationBarsPadding()
                    .windowInsetsPadding(displayCutoutWindowInsets)
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
                .windowInsetsPadding(displayCutoutWindowInsets),
            state = lazyListState,
            contentPadding = innerPadding
        ) {
            item {
                var languageDialog by remember { mutableStateOf(false) }

                if (languageDialog) {
                    SettingsDialog(
                        icon = MoviesIcons.Language,
                        title = stringResource(R.string.settings_language),
                        items = AppLanguage.VALUES,
                        currentItem = currentLanguage,
                        onItemSelect = onLanguageSelect,
                        onDismissRequest = { languageDialog = false }
                    )
                }

                SettingItem(
                    title = stringResource(R.string.settings_language),
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
                        title = stringResource(R.string.settings_theme),
                        items = AppTheme.VALUES,
                        currentItem = currentTheme,
                        onItemSelect = onThemeSelect,
                        onDismissRequest = { themeDialog = false }
                    )
                }

                SettingItem(
                    title = stringResource(R.string.settings_theme),
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
                        title = stringResource(R.string.settings_appearance),
                        items = FeedView.VALUES,
                        currentItem = currentFeedView,
                        onItemSelect = onFeedViewSelect,
                        onDismissRequest = { appearanceDialog = false }
                    )
                }

                SettingItem(
                    title = stringResource(R.string.settings_appearance),
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
                        title = stringResource(R.string.settings_movie_list),
                        items = MovieList.VALUES,
                        currentItem = currentMovieList,
                        onItemSelect = onMovieListSelect,
                        onDismissRequest = { movieListDialog = false }
                    )
                }

                SettingItem(
                    title = stringResource(R.string.settings_movie_list),
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
                    val grammaticalInflectionManager by remember { mutableStateOf(context.getSystemService(GrammaticalInflectionManager::class.java)) }
                    val grammaticalGender by remember { mutableStateOf(grammaticalInflectionManager.applicationGrammaticalGender) }
                    val currentGrammaticalGender by remember { mutableStateOf(GrammaticalGender.transform(grammaticalGender)) }
                    var genderDialog by remember { mutableStateOf(false) }

                    if (genderDialog) {
                        SettingsDialog(
                            icon = MoviesIcons.Cat,
                            title = stringResource(R.string.settings_gender),
                            items = GrammaticalGender.VALUES,
                            currentItem = currentGrammaticalGender,
                            onItemSelect = { item ->
                                grammaticalInflectionManager.setRequestedApplicationGrammaticalGender(item.value)
                            },
                            onDismissRequest = { genderDialog = false }
                        )
                    }

                    SettingItem(
                        title = stringResource(R.string.settings_gender),
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
                        title = stringResource(R.string.settings_dynamic_colors),
                        description = stringResource(R.string.settings_dynamic_colors_description),
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
                    val notificationManager by remember { mutableStateOf(context.notificationManager) }
                    var areNotificationsEnabled by remember { mutableStateOf(notificationManager.areNotificationsEnabled()) }

                    val postNotificationsPermission = rememberLauncherForActivityResult(
                        ActivityResultContracts.RequestPermission()
                    ) { granted ->
                        if (granted) {
                            areNotificationsEnabled = notificationManager.areNotificationsEnabled()
                        } else {
                            val shouldRequest = (context as Activity).shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS)
                            if (!shouldRequest) {
                                onShowPermissionSnackbar()
                            }
                        }
                    }

                    OnResume {
                        areNotificationsEnabled = notificationManager.areNotificationsEnabled()
                    }

                    SettingSwitchItem(
                        title = stringResource(R.string.settings_post_notifications),
                        description = stringResource(if (areNotificationsEnabled) R.string.settings_post_notifications_granted else R.string.settings_post_notifications_denied),
                        icon = MoviesIcons.Notifications,
                        checked = areNotificationsEnabled,
                        onClick = {
                            if (areNotificationsEnabled) {
                                resultContract.launch(context.appNotificationSettingsIntent)
                            } else {
                                postNotificationsPermission.launch(Manifest.permission.POST_NOTIFICATIONS)
                            }
                        }
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
                        title = stringResource(R.string.settings_lock_app),
                        description = stringResource(if (isBiometricEnabled) R.string.settings_biometric_added else R.string.settings_biometric_not_added),
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
                val appWidgetManager by remember { mutableStateOf(AppWidgetManager.getInstance(context)) }
                val appWidgetProvider by remember { mutableStateOf(appWidgetManager.getInstalledProvidersForPackage(context.packageName, null).first()) }

                SettingItem(
                    title = stringResource(R.string.settings_app_widget),
                    description = stringResource(R.string.settings_app_widget_description, stringResource(WidgetR.string.appwidget_description)),
                    icon = MoviesIcons.Widgets,
                    onClick = { appWidgetProvider.pin(context) }
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
                    fun onRequestAddTileService() {
                        val statusBarManager = ContextCompat.getSystemService(context, StatusBarManager::class.java)
                        statusBarManager?.requestAddTileService(
                            ComponentName(context, MoviesTileService::class.java),
                            context.getString(UiR.string.tile_title),
                            Icon.createWithResource(context, MoviesAndroidIcons.MovieFilter24),
                            context.mainExecutor
                        ) { result ->
                            when (result) {
                                StatusBarManager.TILE_ADD_REQUEST_RESULT_TILE_ALREADY_ADDED -> onShowSnackbar(context.getString(R.string.settings_tile_error_already_added))
                            }
                        }
                    }

                    SettingItem(
                        title = stringResource(R.string.settings_tile),
                        description = stringResource(R.string.settings_tile_description),
                        icon = MoviesIcons.ViewAgenda,
                        onClick = { onRequestAddTileService() }
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
                    text = stringResource(R.string.settings_app_launcher_icon),
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
                        onClick = { icon ->
                            onShowSnackbar(context.getString(R.string.settings_app_launcher_icon_changed_to, context.getString(icon.iconSnackbarTextRes)))
                            context.setIcon(icon)
                        }
                    )

                    SettingAppIcon(
                        iconAlias = IconAlias.Purple,
                        onClick = { icon ->
                            onShowSnackbar(context.getString(R.string.settings_app_launcher_icon_changed_to, context.getString(icon.iconSnackbarTextRes)))
                            context.setIcon(icon)
                        }
                    )

                    SettingAppIcon(
                        iconAlias = IconAlias.Brown,
                        onClick = { icon ->
                            onShowSnackbar(context.getString(R.string.settings_app_launcher_icon_changed_to, context.getString(icon.iconSnackbarTextRes)))
                            context.setIcon(icon)
                        }
                    )

                    SettingAppIcon(
                        iconAlias = IconAlias.Amoled,
                        onClick = { icon ->
                            onShowSnackbar(context.getString(R.string.settings_app_launcher_icon_changed_to, context.getString(icon.iconSnackbarTextRes)))
                            context.setIcon(icon)
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
                val toolbarColor = MaterialTheme.colorScheme.primary.toArgb()

                SettingItem(
                    title = stringResource(R.string.settings_github),
                    description = stringResource(R.string.settings_github_description),
                    icon = MoviesIcons.Github,
                    onClick = { openUrlAndroid(resultContract, toolbarColor, MOVIES_GITHUB_URL) }
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
                        title = stringResource(R.string.settings_review),
                        description = stringResource(R.string.settings_review_description),
                        icon = MoviesIcons.GooglePlay,
                        onClick = { onRequestReview(context as Activity) }
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
                        title = stringResource(R.string.settings_update),
                        description = stringResource(R.string.settings_update_description),
                        icon = MoviesIcons.SystemUpdate,
                        onClick = { onRequestUpdate(context as Activity) }
                    )
                }
            }
        }
    }
}