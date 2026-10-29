package org.michaelbel.movies.settingsweb.event

import org.michaelbel.movies.common.mvi.Event

sealed interface SettingsEvent: Event {
    data object GithubClick: SettingsEvent
    data object TelegramClick: SettingsEvent
}
