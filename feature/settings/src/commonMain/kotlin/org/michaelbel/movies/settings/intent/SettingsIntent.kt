package org.michaelbel.movies.settings.intent

import org.michaelbel.movies.common.appearance.FeedView
import org.michaelbel.movies.common.list.MovieList
import org.michaelbel.movies.common.mvi.Intent
import org.michaelbel.movies.common.theme.AppTheme
import org.michaelbel.movies.interactor.entity.AppLanguage
import org.michaelbel.movies.interactor.entity.AppProvider
import org.michaelbel.movies.ui.appicon.IconAlias

sealed interface SettingsIntent: Intent {
    data object CollectThemeData: SettingsIntent
    data object CollectFeedView: SettingsIntent
    data object CollectMovieList: SettingsIntent
    data object CollectProvider: SettingsIntent
    data object CollectAppServiceData: SettingsIntent
    data object CollectNotificationsEnabled: SettingsIntent
    data object CollectDoNotDisturbState: SettingsIntent
    data object CollectIgnoringBatteryOptimizations: SettingsIntent
    data object CollectBiometricFeatureEnabled: SettingsIntent
    data object CollectBiometricEnabled: SettingsIntent
    data object CollectScreenshotBlockEnabled: SettingsIntent
    data object CollectGender: SettingsIntent
    data object CollectAbout: SettingsIntent
    data object CollectFeaturesEnabled: SettingsIntent
    data object CollectAppIcon: SettingsIntent
    data object FetchUpdateAvailable: SettingsIntent
    data object RequestPostNotificationsPermission: SettingsIntent
    data object RequestDoNotDisturbAccess: SettingsIntent
    data object RequestIgnoreBatteryOptimizations: SettingsIntent
    data object RequestTileService: SettingsIntent
    data object RequestGithub: SettingsIntent
    data object RequestTelegram: SettingsIntent
    data object RequestGooglePlay: SettingsIntent
    data object BackClick: SettingsIntent
    data object ReviewClick: SettingsIntent
    data object UpdateClick: SettingsIntent
    data object ScrollToTop: SettingsIntent
    data object ResetSettings: SettingsIntent
    data object OpenDebugDialog: SettingsIntent
    data class ShowSnackbar(val message: String): SettingsIntent
    data class ShowPermissionSnackbar(val message: String, val actionLabel: String): SettingsIntent
    data class SelectLanguage(val language: AppLanguage): SettingsIntent
    data class SelectTheme(val theme: AppTheme): SettingsIntent
    data class SelectFeedView(val feedView: FeedView): SettingsIntent
    data class SelectMovieList(val movieList: MovieList): SettingsIntent
    data class SelectProvider(val provider: AppProvider): SettingsIntent
    data class SetDynamicColors(val value: Boolean): SettingsIntent
    data class SetPaletteColors(val value: Boolean): SettingsIntent
    data class SetPaletteKey(val paletteKey: Int): SettingsIntent
    data class SetSeedColor(val seedColor: Int): SettingsIntent
    data class SetBiometricEnabled(val enabled: Boolean): SettingsIntent
    data class SetDoNotDisturbEnabled(val enabled: Boolean): SettingsIntent
    data class SetScreenshotBlockEnabled(val enabled: Boolean): SettingsIntent
    data class SetUpdateAvailable(val state: Boolean): SettingsIntent
    data class SetGrammaticalGender(val value: Int): SettingsIntent
    data class SetAppIcon(val icon: IconAlias): SettingsIntent
}
