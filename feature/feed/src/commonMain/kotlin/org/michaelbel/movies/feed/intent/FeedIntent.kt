package org.michaelbel.movies.feed.intent

import org.michaelbel.movies.common.mvi.Intent
import org.michaelbel.movies.persistence.database.typealiases.MovieId
import org.michaelbel.movies.persistence.database.typealiases.PagingKey

sealed interface FeedIntent: Intent {
    data object CollectAccountPojo: FeedIntent
    data object CollectFeedView: FeedIntent
    data object CollectMovieList: FeedIntent
    data object CollectProvider: FeedIntent
    data object CollectNetworkStatus: FeedIntent
    data object CollectPageFailureButtonVisible: FeedIntent
    data object CollectFeatureFlags: FeedIntent
    data object CollectSuggestions: FeedIntent
    data object CollectSearchHistoryMovies: FeedIntent
    data object LoadSuggestions: FeedIntent
    data object SubscribeNotificationsPermissionRequired: FeedIntent
    data object SettingsClick: FeedIntent
    data object AuthClick: FeedIntent
    data object AccountClick: FeedIntent
    data object ClearSearchHistoryClick: FeedIntent
    data class RemoveMovieFromHistoryClick(val movieId: MovieId): FeedIntent
    data class SaveMovieToSearchHistoryClick(val movieId: MovieId): FeedIntent
    data class EnterSearchQuery(val query: String): FeedIntent
    data object ScrollToTop: FeedIntent
    data class ShowSnackbar(val message: String, val isLong: Boolean): FeedIntent
    data class MovieDetailsClick(val pagingKey: PagingKey, val movieId: MovieId): FeedIntent
}
