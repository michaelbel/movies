@file:OptIn(ExperimentalResourceApi::class)

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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.stringResource
import org.koin.androidx.compose.koinViewModel
import org.michaelbel.movies.common.browser.openUrl
import org.michaelbel.movies.common.gender.GrammaticalGender
import org.michaelbel.movies.common.ktx.notificationManager
import org.michaelbel.movies.common.localization.model.AppLanguage
import org.michaelbel.movies.settings.SettingsViewModel
import org.michaelbel.movies.settings.ktx.isDebug
import org.michaelbel.movies.settings.ktx.versionCode
import org.michaelbel.movies.settings.ktx.versionName
import org.michaelbel.movies.ui.appicon.IconAlias
import org.michaelbel.movies.ui.appicon.isEnabled
import org.michaelbel.movies.ui.appicon.setIcon
import org.michaelbel.movies.ui.icons.MoviesAndroidIcons
import org.michaelbel.movies.ui.ktx.appNotificationSettingsIntent
import org.michaelbel.movies.ui.ktx.displayCutoutWindowInsets
import org.michaelbel.movies.ui.lifecycle.OnResume
import org.michaelbel.movies.ui.strings.MoviesStrings
import org.michaelbel.movies.ui.tile.MoviesTileService
import org.michaelbel.movies.widget.ktx.pin
import org.michaelbel.movies.ui_kmp.R as UiR

@Composable
fun SettingsRoute(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SettingsViewModel = koinViewModel()
) {
    val currentLanguage = AppLanguage.transform(stringResource(MoviesStrings.language_code))
    val currentTheme by viewModel.currentTheme.collectAsStateWithLifecycle()
    val currentFeedView by viewModel.currentFeedView.collectAsStateWithLifecycle()
    val currentMovieList by viewModel.currentMovieList.collectAsStateWithLifecycle()
    val dynamicColors by viewModel.dynamicColors.collectAsStateWithLifecycle()
    val isBiometricFeatureEnabled by viewModel.isBiometricFeatureEnabled.collectAsStateWithLifecycle()
    val isBiometricEnabled by viewModel.isBiometricEnabled.collectAsStateWithLifecycle()
    val appVersionData by viewModel.appVersionData.collectAsStateWithLifecycle()

    val context = LocalContext.current
    val resultContract = rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) {}
    val toolbarColor = MaterialTheme.colorScheme.primary.toArgb()
    val tileTitleLabel = stringResource(MoviesStrings.tile_title)
    val tileMessage = stringResource(MoviesStrings.settings_tile_error_already_added)

    val appWidgetManager by remember { mutableStateOf(AppWidgetManager.getInstance(context)) }
    val appWidgetProvider by remember { mutableStateOf(appWidgetManager.getInstalledProvidersForPackage(context.packageName, null).first()) }

    val notificationManager by remember { mutableStateOf(context.notificationManager) }
    var areNotificationsEnabled by remember { mutableStateOf(notificationManager.areNotificationsEnabled()) }

    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    val permissionMessage = stringResource(MoviesStrings.settings_post_notifications_should_request)
    val permissionAction = stringResource(MoviesStrings.settings_action_go)
    val onShowPermissionSnackbar: () -> Unit = {
        scope.launch {
            val result = snackbarHostState.showSnackbar(
                message = permissionMessage,
                actionLabel = permissionAction,
                duration = SnackbarDuration.Long
            )
            if (result == SnackbarResult.ActionPerformed) {
                resultContract.launch(context.appNotificationSettingsIntent)
            }
        }
    }

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

    val grammaticalInflectionManager by remember { mutableStateOf(context.getSystemService(GrammaticalInflectionManager::class.java)) }
    val grammaticalGender by remember { mutableStateOf(grammaticalInflectionManager.applicationGrammaticalGender) }
    val currentGrammaticalGender by remember { mutableStateOf(GrammaticalGender.transform(grammaticalGender)) }

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
        onRequestReview = { viewModel.requestReview(context as Activity) },
        onRequestUpdate = { viewModel.requestUpdate(context as Activity) },
        appVersionData = appVersionData,
        onNavigateToAppNotificationSettings = {
            resultContract.launch(context.appNotificationSettingsIntent)
        },
        versionName = context.versionName,
        versionCode = context.versionCode,
        isDebug = isDebug,
        windowInsets = displayCutoutWindowInsets,
        onNavigateToUrl = { url -> openUrl(resultContract, toolbarColor, url) },
        onRequestAddTileService = { onShowSnackbar ->
            val statusBarManager = ContextCompat.getSystemService(context, StatusBarManager::class.java)
            statusBarManager?.requestAddTileService(
                ComponentName(context, MoviesTileService::class.java),
                tileTitleLabel,
                Icon.createWithResource(context, MoviesAndroidIcons.MovieFilter24),
                context.mainExecutor
            ) { result ->
                when (result) {
                    StatusBarManager.TILE_ADD_REQUEST_RESULT_TILE_ALREADY_ADDED -> {
                        onShowSnackbar(tileMessage)
                    }
                }
            }
        },
        onPinAppWidget = { appWidgetProvider.pin(context) },
        areNotificationsEnabled = areNotificationsEnabled,
        onNotificationEnabledClick = {
            if (areNotificationsEnabled) {
                resultContract.launch(context.appNotificationSettingsIntent)
            } else {
                postNotificationsPermission.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        },
        currentGrammaticalGender = currentGrammaticalGender,
        onGenderSelect = { gender ->
            grammaticalInflectionManager.setRequestedApplicationGrammaticalGender(gender.value)
        },
        isRedIconEnabled = context.isEnabled(IconAlias.Red),
        isPurpleIconEnabled = context.isEnabled(IconAlias.Purple),
        isBrownIconEnabled = context.isEnabled(IconAlias.Brown),
        isAmoledIconEnabled = context.isEnabled(IconAlias.Amoled),
        onSetIcon = { icon -> context.setIcon(icon) },
        snackbarHostState = snackbarHostState,
        modifier = modifier
    )
}