package org.michaelbel.movies.fave.intent

import org.michaelbel.movies.common.mvi.Intent

sealed interface FaveIntent: Intent {
    data object CollectFeedView: FaveIntent
    data object CollectPageFailureButtonVisible: FaveIntent
    data object ScrollToTop: FaveIntent
    data class MovieDetailsClick(val pagingKey: String, val movieId: Int): FaveIntent
}
