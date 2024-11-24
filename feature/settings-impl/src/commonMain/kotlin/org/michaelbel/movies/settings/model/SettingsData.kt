package org.michaelbel.movies.settings.model

import org.michaelbel.movies.common.SealedString
import org.michaelbel.movies.common.appearance.FeedView
import org.michaelbel.movies.common.list.MovieList
import org.michaelbel.movies.common.theme.AppTheme
import org.michaelbel.movies.interactor.entity.AppLanguage
import org.michaelbel.movies.ui.appicon.IconAlias

data class SettingsData(
    val onBackClick: () -> Unit,
    val languageData: ListData<AppLanguage>,
    val themeData: ListData<AppTheme>,
    val feedViewData: ListData<FeedView>,
    val movieListData: ListData<MovieList>,
    val genderData: ListData<SealedString>,
    val dynamicColorsData: ChangedData,
    val paletteColorsData: PaletteColorsData,
    val notificationsData: NotificationsData,
    val biometricData: ChangedData,
    val widgetData: RequestedData,
    val tileData: RequestedData,
    val appIconData: ListData<IconAlias>,
    val screenshotData: ChangedData,
    val githubData: RequestedData,
    val reviewAppData: RequestedData,
    val updateAppData: RequestedData,
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
        override val onSelect: (T) -> Unit = {}
    ): Listed<T>

    data class ChangedData(
        override val isFeatureEnabled: Boolean,
        override val isEnabled: Boolean = false,
        override val onChange: (Boolean) -> Unit = {}
    ): Changed

    data class RequestedData(
        override val isFeatureEnabled: Boolean,
        override val onRequest: () -> Unit = {}
    ): Requested

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
        val onClick: () -> Unit = {}
    ): Featured

    data class AboutData(
        override val isFeatureEnabled: Boolean,
        val versionName: String,
        val versionCode: Long,
        val flavor: String,
        val isDebug: Boolean
    ): Featured
}