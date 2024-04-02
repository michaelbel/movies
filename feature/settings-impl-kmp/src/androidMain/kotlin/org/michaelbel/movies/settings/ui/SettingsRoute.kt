@file:OptIn(ExperimentalResourceApi::class, ExperimentalResourceApi::class)

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
import org.michaelbel.movies.common.MOVIES_GITHUB_URL
import org.michaelbel.movies.common.browser.openUrl
import org.michaelbel.movies.common.gender.GrammaticalGender
import org.michaelbel.movies.common.ktx.notificationManager
import org.michaelbel.movies.common.localization.model.AppLanguage
import org.michaelbel.movies.settings.SettingsViewModel
import org.michaelbel.movies.settings.ktx.iconSnackbarTextRes
import org.michaelbel.movies.settings.ktx.isDebug
import org.michaelbel.movies.settings.ktx.versionCode
import org.michaelbel.movies.settings.ktx.versionName
import org.michaelbel.movies.settings.model.SettingsData
import org.michaelbel.movies.settings.model.isAboutFeatureEnabled
import org.michaelbel.movies.settings.model.isAppIconFeatureEnabled
import org.michaelbel.movies.settings.model.isBiometricFeatureEnabled
import org.michaelbel.movies.settings.model.isDynamicColorsFeatureEnabled
import org.michaelbel.movies.settings.model.isFeedViewFeatureEnabled
import org.michaelbel.movies.settings.model.isGenderFeatureEnabled
import org.michaelbel.movies.settings.model.isGithubFeatureEnabled
import org.michaelbel.movies.settings.model.isLanguageFeatureEnabled
import org.michaelbel.movies.settings.model.isMovieListFeatureEnabled
import org.michaelbel.movies.settings.model.isNotificationsFeatureEnabled
import org.michaelbel.movies.settings.model.isReviewAppFeatureEnabled
import org.michaelbel.movies.settings.model.isThemeFeatureEnabled
import org.michaelbel.movies.settings.model.isTileFeatureEnabled
import org.michaelbel.movies.settings.model.isUpdateAppFeatureEnabled
import org.michaelbel.movies.settings.model.isWidgetFeatureEnabled
import org.michaelbel.movies.ui.appicon.IconAlias
import org.michaelbel.movies.ui.appicon.enabledIcon
import org.michaelbel.movies.ui.appicon.setIcon
import org.michaelbel.movies.ui.icons.MoviesAndroidIcons
import org.michaelbel.movies.ui.ktx.appNotificationSettingsIntent
import org.michaelbel.movies.ui.ktx.displayCutoutWindowInsets
import org.michaelbel.movies.ui.lifecycle.OnResume
import org.michaelbel.movies.ui.strings.MoviesStrings
import org.michaelbel.movies.ui.tile.MoviesTileService
import org.michaelbel.movies.widget.ktx.pin

@Composable
fun SettingsRoute(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SettingsViewModel = koinViewModel()
) {
    val currentLanguage = AppLanguage.transform(stringResource(MoviesStrings.language_code))
    val themeData by viewModel.themeData.collectAsStateWithLifecycle()
    val currentFeedView by viewModel.currentFeedView.collectAsStateWithLifecycle()
    val currentMovieList by viewModel.currentMovieList.collectAsStateWithLifecycle()
    val isBiometricFeatureAvailable by viewModel.isBiometricFeatureEnabled.collectAsStateWithLifecycle()
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

    val onShowSnackbar: (String) -> Unit = { message ->
        scope.launch {
            snackbarHostState.showSnackbar(
                message = message,
                duration = SnackbarDuration.Short
            )
        }
    }

    val messageRed = stringResource(MoviesStrings.settings_app_launcher_icon_changed_to, stringResource(IconAlias.Red.iconSnackbarTextRes))
    val messagePurple = stringResource(MoviesStrings.settings_app_launcher_icon_changed_to, stringResource(IconAlias.Purple.iconSnackbarTextRes))
    val messageBrown = stringResource(MoviesStrings.settings_app_launcher_icon_changed_to, stringResource(IconAlias.Brown.iconSnackbarTextRes))
    val messageAmoled = stringResource(MoviesStrings.settings_app_launcher_icon_changed_to, stringResource(IconAlias.Amoled.iconSnackbarTextRes))

    SettingsScreenContent(
        settingsData = SettingsData(
            onBackClick = onBackClick,
            languageData = SettingsData.ListData(
                isFeatureEnabled = isLanguageFeatureEnabled,
                current = currentLanguage,
                onSelect = viewModel::selectLanguage
            ),
            themeData = SettingsData.ListData(
                isFeatureEnabled = isThemeFeatureEnabled,
                current = themeData.appTheme,
                onSelect = viewModel::selectTheme
            ),
            feedViewData = SettingsData.ListData(
                isFeatureEnabled = isFeedViewFeatureEnabled,
                current = currentFeedView,
                onSelect = viewModel::selectFeedView
            ),
            movieListData = SettingsData.ListData(
                isFeatureEnabled = isMovieListFeatureEnabled,
                current = currentMovieList,
                onSelect = viewModel::selectMovieList
            ),
            genderData = SettingsData.ListData(
                isFeatureEnabled = isGenderFeatureEnabled,
                current = currentGrammaticalGender,
                onSelect = { gender ->
                    grammaticalInflectionManager.setRequestedApplicationGrammaticalGender(GrammaticalGender.value(gender))
                }
            ),
            dynamicColorsData = SettingsData.ChangedData(
                isFeatureEnabled = isDynamicColorsFeatureEnabled,
                isEnabled = themeData.dynamicColors,
                onChange = viewModel::setDynamicColors
            ),
            paletteColorsData = SettingsData.PaletteColorsData(
                isFeatureEnabled = isDynamicColorsFeatureEnabled,
                isDynamicColorsEnabled = themeData.dynamicColors,
                paletteKey = themeData.paletteKey,
                seedColor = themeData.seedColor,
                onChange = { localDynamicColors, localPaletteKey, localSeedColor ->
                    viewModel.run {
                        setDynamicColors(localDynamicColors)
                        setPaletteKey(localPaletteKey)
                        setSeedColor(localSeedColor)
                    }
                }
            ),
            notificationsData = SettingsData.NotificationsData(
                isFeatureEnabled = isNotificationsFeatureEnabled,
                isEnabled = areNotificationsEnabled,
                onClick = {
                    if (areNotificationsEnabled) {
                        resultContract.launch(context.appNotificationSettingsIntent)
                    } else {
                        postNotificationsPermission.launch(Manifest.permission.POST_NOTIFICATIONS)
                    }
                },
                onNavigateToAppNotificationSettings = {
                    resultContract.launch(context.appNotificationSettingsIntent)
                }
            ),
            biometricData = SettingsData.ChangedData(
                isFeatureEnabled = isBiometricFeatureEnabled && isBiometricFeatureAvailable,
                isEnabled = isBiometricEnabled,
                onChange = viewModel::setBiometricEnabled
            ),
            widgetData = SettingsData.RequestedData(
                isFeatureEnabled = isWidgetFeatureEnabled,
                onRequest = { appWidgetProvider.pin(context) }
            ),
            tileData = SettingsData.RequestedData(
                isFeatureEnabled = isTileFeatureEnabled,
                onRequest = {
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
                }
            ),
            appIconData = SettingsData.ListData(
                isFeatureEnabled = isAppIconFeatureEnabled,
                current = context.enabledIcon,
                onSelect = { icon ->
                    val message = when (icon) {
                        IconAlias.Red -> messageRed
                        IconAlias.Purple -> messagePurple
                        IconAlias.Brown -> messageBrown
                        IconAlias.Amoled -> messageAmoled
                    }
                    onShowSnackbar(message)
                    context.setIcon(icon)
                }
            ),
            githubData = SettingsData.RequestedData(
                isFeatureEnabled = isGithubFeatureEnabled,
                onRequest = { openUrl(resultContract, toolbarColor, MOVIES_GITHUB_URL) }
            ),
            reviewAppData = SettingsData.RequestedData(
                isFeatureEnabled = isReviewAppFeatureEnabled && viewModel.isReviewFeatureEnabled,
                onRequest = { viewModel.requestReview(context as Activity) }
            ),
            updateAppData = SettingsData.RequestedData(
                isFeatureEnabled = isUpdateAppFeatureEnabled && viewModel.isUpdateFeatureEnabled && viewModel.isUpdateAvailable,
                onRequest = { viewModel.requestUpdate(context as Activity) }
            ),
            aboutData = SettingsData.AboutData(
                isFeatureEnabled = isAboutFeatureEnabled,
                versionName = context.versionName,
                versionCode = context.versionCode,
                flavor = appVersionData.flavor,
                isDebug = isDebug
            )
        ),
        windowInsets = displayCutoutWindowInsets,
        snackbarHostState = snackbarHostState,
        modifier = modifier
    )
}