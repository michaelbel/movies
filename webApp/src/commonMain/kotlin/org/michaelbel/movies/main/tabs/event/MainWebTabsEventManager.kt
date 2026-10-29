package org.michaelbel.movies.main.tabs.event

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import org.michaelbel.movies.main.MainWebDestination

object MainWebTabsEventManager {

    private val _reselectFlow = MutableSharedFlow<MainWebDestination>(extraBufferCapacity = 1)
    val reselectFlow: SharedFlow<MainWebDestination> = _reselectFlow

    fun reselect(destination: MainWebDestination) {
        _reselectFlow.tryEmit(destination)
    }
}
