package org.michaelbel.movies.settings.event

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import org.michaelbel.movies.common.mvi.Event

object SettingsEventManager {

    private val _eventChannel = Channel<Event>()
    val eventFlow: Flow<Event> = _eventChannel.receiveAsFlow()

    suspend fun push(event: Event) {
        _eventChannel.send(event)
    }
}
