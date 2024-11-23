package org.michaelbel.movies.feed

import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.stateIn
import org.michaelbel.movies.common.appearance.FeedView
import org.michaelbel.movies.common.list.MovieList
import org.michaelbel.movies.common.viewmodel.BaseViewModel

class FeedViewModel: BaseViewModel() {

    val currentFeedView: StateFlow<FeedView> = flowOf(FeedView.FeedList)
        .stateIn(
            scope = scope,
            started = SharingStarted.Lazily,
            initialValue = FeedView.FeedList
        )

    val currentMovieList: StateFlow<MovieList> = flowOf(MovieList.NowPlaying())
        .stateIn(
            scope = scope,
            started = SharingStarted.Lazily,
            initialValue = MovieList.NowPlaying()
        )
}