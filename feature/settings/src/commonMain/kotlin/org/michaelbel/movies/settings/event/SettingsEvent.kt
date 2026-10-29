package org.michaelbel.movies.settings.event

import org.michaelbel.movies.common.mvi.Event

sealed interface SettingsEvent: Event {
    data object PinWidget: SettingsEvent
    data object RequestPostNotificationsPermission: SettingsEvent
    data object RequestDoNotDisturbAccess: SettingsEvent
    data object RequestIgnoreBatteryOptimizations: SettingsEvent
    data object RequestTileService: SettingsEvent
    data object RequestGithub: SettingsEvent
    data object RequestTelegram: SettingsEvent
    data object RequestGooglePlay: SettingsEvent
    data object ScrollToTop: SettingsEvent
    data object ReselectSettings: SettingsEvent
    data class ShowSnackbar(val message: String): SettingsEvent
    data class ShowPermissionSnackbar(val message: String, val actionLabel: String): SettingsEvent
}
