package org.michaelbel.movies.settings.ui

import androidx.compose.foundation.clickable
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun SettingsRoute(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    //viewModel: SettingsViewModel = koinInject<SettingsViewModel>()
) {
    Text(
        text = "feed",
        modifier = Modifier.clickable { onBackClick() }
    )
    /*val currentLanguage = AppLanguage.transform(stringResource(MoviesStrings.language_code))
    val themeData by viewModel.themeData.collectAsStateCommon()
    val currentFeedView by viewModel.currentFeedView.collectAsStateCommon()
    val currentMovieList by viewModel.currentMovieList.collectAsStateCommon()
    val snackbarHostState = remember { SnackbarHostState() }

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
                current = GrammaticalGender.NotSpecified(),
                onSelect = {}
            ),
            dynamicColorsData = SettingsData.ChangedData(
                isFeatureEnabled = isDynamicColorsFeatureEnabled,
                isEnabled = false,
                onChange = {}
            ),
            paletteColorsData = SettingsData.PaletteColorsData(
                isFeatureEnabled = true,
                isDynamicColorsEnabled = false,
                paletteKey = themeData.paletteKey,
                seedColor = themeData.seedColor,
                onChange = { _, localPaletteKey, localSeedColor ->
                    viewModel.run {
                        setPaletteKey(localPaletteKey)
                        setSeedColor(localSeedColor)
                    }
                }
            ),
            notificationsData = SettingsData.NotificationsData(
                isFeatureEnabled = isNotificationsFeatureEnabled,
                isEnabled = false,
                onClick = {},
                onNavigateToAppNotificationSettings = {}
            ),
            biometricData = SettingsData.ChangedData(
                isFeatureEnabled = isBiometricFeatureEnabled,
                isEnabled = false,
                onChange = {}
            ),
            widgetData = SettingsData.RequestedData(
                isFeatureEnabled = isWidgetFeatureEnabled,
                onRequest = {}
            ),
            tileData = SettingsData.RequestedData(
                isFeatureEnabled = isTileFeatureEnabled,
                onRequest = {}
            ),
            appIconData = SettingsData.ListData(
                isFeatureEnabled = isAppIconFeatureEnabled,
                current = IconAlias.Red,
                onSelect = {}
            ),
            screenshotData = SettingsData.ChangedData(
                isFeatureEnabled = isScreenshotFeatureEnabled,
                isEnabled = false,
                onChange = {}
            ),
            githubData = SettingsData.RequestedData(
                isFeatureEnabled = isGithubFeatureEnabled,
                onRequest = viewModel::navigateToGithubUrl
            ),
            reviewAppData = SettingsData.RequestedData(
                isFeatureEnabled = isReviewAppFeatureEnabled,
                onRequest = {}
            ),
            updateAppData = SettingsData.RequestedData(
                isFeatureEnabled = isUpdateAppFeatureEnabled,
                onRequest = {}
            ),
            aboutData = SettingsData.AboutData(
                isFeatureEnabled = isAboutFeatureEnabled,
                versionName = "1.0.0",
                versionCode = 1L,
                flavor = "FOSS",
                isDebug = false
            )
        ),
        windowInsets = WindowInsets(0.dp, 0.dp, 0.dp, 0.dp),
        snackbarHostState = snackbarHostState,
        modifier = modifier
    )*/
}