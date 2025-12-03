package org.michaelbel.movies.ui.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow

object MainNavigator {

    sealed interface NavigationEvent {
        data class Forward(val destination: NavKey) : NavigationEvent
        data object Back : NavigationEvent
        data object RequestReview : NavigationEvent
        data object RequestUpdate : NavigationEvent
    }

    private val _destChannel = Channel<NavigationEvent>()
    val destFlow: Flow<NavigationEvent> = _destChannel.receiveAsFlow()

    suspend fun forward(destination: NavKey) {
        _destChannel.send(NavigationEvent.Forward(destination))
    }

    suspend fun back() {
        _destChannel.send(NavigationEvent.Back)
    }

    suspend fun requestReview() {
        _destChannel.send(NavigationEvent.RequestReview)
    }

    suspend fun requestUpdate() {
        _destChannel.send(NavigationEvent.RequestUpdate)
    }
}