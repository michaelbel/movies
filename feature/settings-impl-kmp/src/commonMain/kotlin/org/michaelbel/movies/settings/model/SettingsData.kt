package org.michaelbel.movies.settings.model

import org.michaelbel.movies.common.appearance.FeedView
import org.michaelbel.movies.common.gender.GrammaticalGender
import org.michaelbel.movies.common.list.MovieList
import org.michaelbel.movies.common.localization.model.AppLanguage
import org.michaelbel.movies.common.theme.AppTheme
import org.michaelbel.movies.ui.appicon.IconAlias

data class SettingsData(
    val onBackClick: () -> Unit,
    val languageData: ListData<AppLanguage>,
    val themeData: ListData<AppTheme>,
    val feedViewData: ListData<FeedView>,
    val movieListData: ListData<MovieList>,
    val genderData: ListData<GrammaticalGender>,
    val dynamicColorsData: DynamicColorsData,
    val paletteColorsData: PaletteColorsData,
    val notificationsData: NotificationsData,
    val biometricData: BiometricData,
    val widgetData: WidgetData,
    val tileData: TileData,
    val appIconData: ListData<IconAlias>,
    val githubData: GithubData,
    val reviewAppData: ReviewAppData,
    val updateAppData: UpdateAppData,
    val aboutData: AboutData
) {
    interface Featured {
        val isFeatureEnabled: Boolean
    }

    interface Listed<T>: Featured {
        val current: T
        val onSelect: (T) -> Unit
    }

    interface Requested: Featured {
        val onRequest: () -> Unit
    }

    interface Changed: Featured {
        val isEnabled: Boolean
        val onChange: (Boolean) -> Unit
    }

    data class ListData<T>(
        override val isFeatureEnabled: Boolean,
        override val current: T,
        override val onSelect: (T) -> Unit
    ): Listed<T>

    data class DynamicColorsData(
        override val isFeatureEnabled: Boolean,
        override val isEnabled: Boolean,
        override val onChange: (Boolean) -> Unit
    ): Changed

    data class PaletteColorsData(
        override val isFeatureEnabled: Boolean,
        val isDynamicColorsEnabled: Boolean,
        val paletteKey: Int,
        val seedColor: Int,
        val onChange: (Boolean, Int, Int) -> Unit
    ): Featured

    data class NotificationsData(
        override val isFeatureEnabled: Boolean,
        val isEnabled: Boolean,
        val onClick: () -> Unit,
        val onNavigateToAppNotificationSettings: () -> Unit
    ): Featured

    data class BiometricData(
        override val isFeatureEnabled: Boolean,
        override val isEnabled: Boolean,
        override val onChange: (Boolean) -> Unit
    ): Changed

    data class WidgetData(
        override val isFeatureEnabled: Boolean,
        override val onRequest: () -> Unit
    ): Requested

    data class TileData(
        override val isFeatureEnabled: Boolean,
        override val onRequest: () -> Unit
    ): Requested

    data class GithubData(
        override val isFeatureEnabled: Boolean,
        val onClick: (String) -> Unit
    ): Featured

    data class ReviewAppData(
        override val isFeatureEnabled: Boolean,
        override val onRequest: () -> Unit
    ): Requested

    data class UpdateAppData(
        override val isFeatureEnabled: Boolean,
        override val onRequest: () -> Unit
    ): Requested

    data class AboutData(
        override val isFeatureEnabled: Boolean,
        val versionName: String,
        val versionCode: Long,
        val flavor: String,
        val isDebug: Boolean
    ): Featured
}