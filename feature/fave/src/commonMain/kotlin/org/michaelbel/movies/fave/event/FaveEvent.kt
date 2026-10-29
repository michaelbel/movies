package org.michaelbel.movies.fave.event

import org.michaelbel.movies.common.mvi.Event

sealed interface FaveEvent: Event {
    data object ReselectFave: FaveEvent
    data object ScrollToTop: FaveEvent
}
