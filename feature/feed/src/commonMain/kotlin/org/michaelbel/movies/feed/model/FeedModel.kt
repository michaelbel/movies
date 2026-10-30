package org.michaelbel.movies.feed.model

import org.michaelbel.movies.common.appearance.FeedView
import org.michaelbel.movies.common.list.MovieList
import org.michaelbel.movies.common.mvi.model.Model
import org.michaelbel.movies.interactor.entity.AppProvider
import org.michaelbel.movies.network.connectivity.NetworkStatus
import org.michaelbel.movies.persistence.database.entity.pojo.AccountPojo
import org.michaelbel.movies.persistence.database.entity.pojo.MoviePojo
import org.michaelbel.movies.persistence.database.entity.pojo.SuggestionPojo

data class FeedModel(
    val accountPojo: AccountPojo = AccountPojo.Empty,
    val feedView: FeedView = FeedView.FeedList,
    val movieList: MovieList = MovieList.NowPlaying(),
    val provider: AppProvider = AppProvider.Tmdb,
    val networkStatus: NetworkStatus = NetworkStatus.Unavailable,
    val isPageFailureButtonVisible: Boolean = false,
    val isFeedAuthIconFeatureEnabled: Boolean = false,
    val isFeedVoiceInputFeatureEnabled: Boolean = false,
    val isSearchLoading: Boolean = false,
    val searchFailure: Throwable? = null,
    val suggestionPojos: List<SuggestionPojo> = emptyList(),
    val searchHistoryMoviePojos: List<MoviePojo> = emptyList()
): Model
