package org.michaelbel.movies.settings.ui

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.koinInject
import org.michaelbel.movies.common.MOVIES_GITHUB_URL
import org.michaelbel.movies.common.browser.openUrl
import org.michaelbel.movies.common.gender.GrammaticalGender
import org.michaelbel.movies.interactor.entity.AppLanguage
import org.michaelbel.movies.settings.SettingsViewModel
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
import org.michaelbel.movies.settings.model.isScreenshotFeatureEnabled
import org.michaelbel.movies.settings.model.isThemeFeatureEnabled
import org.michaelbel.movies.settings.model.isTileFeatureEnabled
import org.michaelbel.movies.settings.model.isUpdateAppFeatureEnabled
import org.michaelbel.movies.settings.model.isWidgetFeatureEnabled
import org.michaelbel.movies.ui.appicon.IconAlias
import org.michaelbel.movies.ui.ktx.collectAsStateCommon
import org.michaelbel.movies.ui.strings.MoviesStrings

@Composable
fun SettingsRoute(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SettingsViewModel = koinInject()
) {
    val currentLanguage = AppLanguage.transform(stringResource(MoviesStrings.language_code))
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
                current = GrammaticalGender.NotSpecified()
            ),
            dynamicColorsData = SettingsData.ChangedData(
                isFeatureEnabled = isDynamicColorsFeatureEnabled
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
                isEnabled = false
            ),
            biometricData = SettingsData.ChangedData(
                isFeatureEnabled = isBiometricFeatureEnabled
            ),
            widgetData = SettingsData.RequestedData(
                isFeatureEnabled = isWidgetFeatureEnabled
            ),
            tileData = SettingsData.RequestedData(
                isFeatureEnabled = isTileFeatureEnabled
            ),
            appIconData = SettingsData.ListData(
                isFeatureEnabled = isAppIconFeatureEnabled,
                current = IconAlias.Red
            ),
            screenshotData = SettingsData.ChangedData(
                isFeatureEnabled = isScreenshotFeatureEnabled
            ),
            githubData = SettingsData.RequestedData(
                isFeatureEnabled = isGithubFeatureEnabled,
                onRequest = { openUrl(MOVIES_GITHUB_URL) }
            ),
            reviewAppData = SettingsData.RequestedData(
                isFeatureEnabled = isReviewAppFeatureEnabled
            ),
            updateAppData = SettingsData.RequestedData(
                isFeatureEnabled = isUpdateAppFeatureEnabled
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
        isNavigationIconVisible = true,
        modifier = modifier
    )
}