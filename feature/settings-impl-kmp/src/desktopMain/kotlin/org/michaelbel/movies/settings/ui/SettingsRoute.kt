package org.michaelbel.movies.settings.ui

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.michaelbel.movies.common.appearance.FeedView
import org.michaelbel.movies.common.gender.GrammaticalGender
import org.michaelbel.movies.common.list.MovieList
import org.michaelbel.movies.common.localization.model.AppLanguage
import org.michaelbel.movies.common.theme.AppTheme
import org.michaelbel.movies.common.version.AppVersionData

@Composable
fun SettingsRoute(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val snackbarHostState = remember { SnackbarHostState() }

    SettingsScreenContent(
        onBackClick = onBackClick,
        currentLanguage = AppLanguage.English,
        onLanguageSelect = {},
        currentTheme = AppTheme.FollowSystem,
        onThemeSelect = {},
        currentFeedView = FeedView.FeedList,
        onFeedViewSelect = {},
        currentMovieList = MovieList.NowPlaying,
        onMovieListSelect = {},
        isGrammaticalGenderFeatureEnabled = true,
        isDynamicColorsFeatureEnabled = true,
        dynamicColors = true,
        onSetDynamicColors = {},
        isPostNotificationsFeatureEnabled = true,
        isTileFeatureEnabled = true,
        isBiometricFeatureEnabled = true,
        isBiometricEnabled = true,
        onSetBiometricEnabled = {},
        isReviewFeatureEnabled = true,
        isUpdateFeatureEnabled = true,
        onRequestReview = {},
        onRequestUpdate = {},
        appVersionData = AppVersionData("GMS"),
        onNavigateToAppNotificationSettings = {},
        versionName = "1.0.0",
        versionCode = 1L,
        isDebug = false,
        windowInsets = WindowInsets(left = 0.dp, right = 0.dp),
        onNavigateToUrl = {},
        onRequestAddTileService = {},
        onPinAppWidget = {},
        areNotificationsEnabled = true,
        onNotificationEnabledClick = {},
        currentGrammaticalGender = GrammaticalGender.Neutral,
        onGenderSelect = {},
        isRedIconEnabled = true,
        isPurpleIconEnabled = false,
        isBrownIconEnabled = false,
        isAmoledIconEnabled = false,
        onSetIcon = {},
        snackbarHostState = snackbarHostState,
        modifier = modifier
    )
}