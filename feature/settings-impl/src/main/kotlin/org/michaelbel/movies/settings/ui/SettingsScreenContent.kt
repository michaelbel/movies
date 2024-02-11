package org.michaelbel.movies.settings.ui

import android.app.Activity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.launch
import org.michaelbel.movies.common.appearance.FeedView
import org.michaelbel.movies.common.list.MovieList
import org.michaelbel.movies.common.localization.model.AppLanguage
import org.michaelbel.movies.common.theme.AppTheme
import org.michaelbel.movies.common.version.AppVersionData
import org.michaelbel.movies.settings.SettingsViewModel
import org.michaelbel.movies.settings.ktx.iconSnackbarText
import org.michaelbel.movies.settings_impl.R
import org.michaelbel.movies.ui.ktx.appNotificationSettingsIntent
import org.michaelbel.movies.ui.ktx.clickableWithoutRipple
import org.michaelbel.movies.ui.ktx.displayCutoutWindowInsets
import org.michaelbel.movies.ui.preview.DevicePreviews
import org.michaelbel.movies.ui.theme.MoviesTheme
import org.michaelbel.movies.ui.R as UiR

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
    val isPlayServicesAvailable by viewModel.isPlayServicesAvailable.collectAsStateWithLifecycle()
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
        isDynamicColorsFeatureEnabled = viewModel.isDynamicColorsFeatureEnabled,
        dynamicColors = dynamicColors,
        onSetDynamicColors = viewModel::setDynamicColors,
        isPostNotificationsFeatureEnabled = viewModel.isPostNotificationsFeatureEnabled,
        isPlayServicesAvailable = isPlayServicesAvailable,
        onRequestReview = viewModel::requestReview,
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
    isDynamicColorsFeatureEnabled: Boolean,
    dynamicColors: Boolean,
    onSetDynamicColors: (Boolean) -> Unit,
    isPostNotificationsFeatureEnabled: Boolean,
    isPlayServicesAvailable: Boolean,
    onRequestReview: (Activity) -> Unit,
    appVersionData: AppVersionData,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    val topAppBarScrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    val lazyListState = rememberLazyListState()
    val resultContract = rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) {}

    val onShowPermissionSnackbar: () -> Unit = {
        scope.launch {
            val result: SnackbarResult = snackbarHostState.showSnackbar(
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

    fun onLaunchReviewFlow() {
        when {
            !isPlayServicesAvailable -> onShowSnackbar(context.getString(R.string.settings_error_play_services_not_available))
            else -> onRequestReview(context as Activity)
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
                SettingsLanguageBox(
                    currentLanguage = currentLanguage,
                    onLanguageSelect = onLanguageSelect,
                    modifier = Modifier.fillMaxWidth().height(52.dp)
                )
            }
            item {
                Divider(
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)
                )
            }
            item {
                SettingsThemeBox(
                    currentTheme = currentTheme,
                    onThemeSelect = onThemeSelect,
                    modifier = Modifier.fillMaxWidth().height(52.dp)
                )
            }
            item {
                Divider(
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)
                )
            }
            item {
                SettingsAppearanceBox(
                    currentFeedView = currentFeedView,
                    onFeedViewSelect = onFeedViewSelect,
                    modifier = Modifier.fillMaxWidth().height(52.dp)
                )
            }
            item {
                Divider(
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)
                )
            }
            item {
                SettingsMovieListBox(
                    currentMovieList = currentMovieList,
                    onMovieListSelect = onMovieListSelect,
                    modifier = Modifier.fillMaxWidth().height(52.dp)
                )
            }
            item {
                Divider(
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)
                )
            }
            item {
                if (isDynamicColorsFeatureEnabled) {
                    SettingsDynamicColorsBox(
                        isDynamicColorsEnabled = dynamicColors,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(52.dp)
                            .clickable { onSetDynamicColors(!dynamicColors) }
                    )
                }
            }
            item {
                Divider(
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)
                )
            }
            item {
                if (isPostNotificationsFeatureEnabled) {
                    SettingsPostNotificationsBox(
                        onShowPermissionSnackbar = onShowPermissionSnackbar,
                        modifier = Modifier.fillMaxWidth().height(52.dp)
                    )
                }
            }
            item {
                Divider(
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)
                )
            }
            item {
                SettingsReviewBox(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp)
                        .clickable { onLaunchReviewFlow() }
                )
            }
            item {
                Divider(
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)
                )
            }
            item {
                SettingsAppIconBox(
                    onAppIconChanged = { iconAlias ->
                        onShowSnackbar(context.getString(R.string.settings_app_launcher_icon_changed_to, iconAlias.iconSnackbarText(context)))
                    },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

@Composable
@DevicePreviews
private fun SettingsScreenContentPreview() {
    MoviesTheme {
        SettingsScreenContent(
            onBackClick = {},
            currentLanguage = AppLanguage.English,
            onLanguageSelect = {},
            currentTheme = AppTheme.NightNo,
            onThemeSelect = {},
            currentFeedView = FeedView.FeedList,
            onFeedViewSelect = {},
            currentMovieList = MovieList.NowPlaying,
            onMovieListSelect = {},
            isDynamicColorsFeatureEnabled = true,
            dynamicColors = true,
            onSetDynamicColors = {},
            isPostNotificationsFeatureEnabled = true,
            isPlayServicesAvailable = true,
            onRequestReview = {},
            appVersionData = AppVersionData.Empty,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .background(MaterialTheme.colorScheme.primaryContainer)
        )
    }
}

@Composable
@Preview
private fun SettingsScreenContentAmoledPreview() {
    MoviesTheme(
        theme = AppTheme.Amoled
    ) {
        SettingsScreenContent(
            onBackClick = {},
            currentLanguage = AppLanguage.English,
            onLanguageSelect = {},
            currentTheme = AppTheme.NightNo,
            onThemeSelect = {},
            currentFeedView = FeedView.FeedList,
            onFeedViewSelect = {},
            currentMovieList = MovieList.NowPlaying,
            onMovieListSelect = {},
            isDynamicColorsFeatureEnabled = true,
            dynamicColors = true,
            onSetDynamicColors = {},
            isPostNotificationsFeatureEnabled = true,
            isPlayServicesAvailable = true,
            onRequestReview = {},
            appVersionData = AppVersionData.Empty,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .background(MaterialTheme.colorScheme.primaryContainer)
        )
    }
}