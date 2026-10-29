package org.michaelbel.movies.settings.model

import org.michaelbel.movies.common.SealedString
import org.michaelbel.movies.common.ThemeData
import org.michaelbel.movies.common.appearance.FeedView
import org.michaelbel.movies.common.gender.GrammaticalGender
import org.michaelbel.movies.common.list.MovieList
import org.michaelbel.movies.common.mvi.model.Model
import org.michaelbel.movies.common.version.AppVersionData
import org.michaelbel.movies.interactor.entity.AppProvider
import org.michaelbel.movies.ui.appicon.IconAlias

data class SettingsModel(
    val isLanguageFeatureEnabled: Boolean = false,
    val isThemeFeatureEnabled: Boolean = false,
    val isFeedViewFeatureEnabled: Boolean = false,
    val isMovieListFeatureEnabled: Boolean = false,
    val isGenderFeatureEnabled: Boolean = false,
    val isDynamicColorsFeatureEnabled: Boolean = false,
    val isPaletteColorsFeatureEnabled: Boolean = false,
    val isNotificationsFeatureEnabled: Boolean = false,
    val isDoNotDisturbFeatureEnabled: Boolean = false,
    val isBatteryOptimizationFeatureEnabled: Boolean = false,
    val isBiometricFeatureEnabled: Boolean = false,
    val isWidgetFeatureEnabled: Boolean = false,
    val isTileFeatureEnabled: Boolean = false,
    val isAppIconFeatureEnabled: Boolean = false,
    val isAppOpenByDefaultFeatureEnabled: Boolean = false,
    val isScreenshotFeatureEnabled: Boolean = false,
    val isGithubFeatureEnabled: Boolean = false,
    val isTelegramFeatureEnabled: Boolean = false,
    val isGooglePlayFeatureEnabled: Boolean = false,
    val isReviewAppFeatureEnabled: Boolean = false,
    val isUpdateAppFeatureEnabled: Boolean = false,
    val isAboutFeatureEnabled: Boolean = false,
    val isSettingsResetFeatureEnabled: Boolean = false,
    val isDebugDialogFeatureEnabled: Boolean = false,
    val themeData: ThemeData = ThemeData.Default,
    val feedView: FeedView = FeedView.FeedList,
    val movieList: MovieList = MovieList.NowPlaying(),
    val provider: AppProvider = AppProvider.Tmdb,
    val isReviewFeatureEnabled: Boolean = false,
    val isUpdateFeatureEnabled: Boolean = false,
    val areNotificationsEnabled: Boolean = false,
    val isDoNotDisturbAccessGranted: Boolean = false,
    val isDoNotDisturbEnabled: Boolean = false,
    val isIgnoringBatteryOptimizations: Boolean = false,
    val appVersionData: AppVersionData = AppVersionData.Empty,
    val isBiometricAvailable: Boolean = false,
    val isBiometricEnabled: Boolean = false,
    val isScreenshotBlockEnabled: Boolean = false,
    val isUpdateAvailable: Boolean = false,
    val versionName: String = "",
    val versionCode: Long = 0L,
    val grammaticalGender: SealedString = GrammaticalGender.NotSpecified(),
    val enabledIcon: IconAlias = IconAlias.Red
): Model
