@file:OptIn(ExperimentalCoroutinesApi::class)

package org.michaelbel.movies.feed

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.paging.compose.collectAsLazyPagingItems
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import org.koin.compose.koinInject
import org.michaelbel.movies.common.appearance.FeedView
import org.michaelbel.movies.domain.usecase.CurrentFeedViewFlowUseCase
import org.michaelbel.movies.domain.usecase.CurrentMovieListFlowUseCase
import org.michaelbel.movies.domain.usecase.MoviesPagingDataUseCase
import org.michaelbel.movies.feed.ktx.toUserMessage
import org.michaelbel.movies.interactor.LocaleInteractor
import org.michaelbel.movies.main.MainWebDestination
import org.michaelbel.movies.main.tabs.event.MainWebTabsEventManager
import org.michaelbel.movies.persistence.database.typealiases.MovieId
import org.michaelbel.movies.persistence.database.typealiases.PagingKey
import org.michaelbel.movies.ui.compose.page.PageContent
import org.michaelbel.movies.ui.compose.page.PageFailure
import org.michaelbel.movies.ui.compose.page.PageLoading
import org.michaelbel.movies.ui.isFailure
import org.michaelbel.movies.ui.refreshThrowable

@Composable
fun FeedScreen(
    onMovieClick: (PagingKey, MovieId) -> Unit = { _, _ -> },
    localeInteractor: LocaleInteractor = koinInject(),
    currentFeedViewFlowUseCase: CurrentFeedViewFlowUseCase = koinInject(),
    currentMovieListFlowUseCase: CurrentMovieListFlowUseCase = koinInject(),
    moviesPagingDataUseCase: MoviesPagingDataUseCase = koinInject()
) {
    val feedView by remember(currentFeedViewFlowUseCase) {
        currentFeedViewFlowUseCase(Unit)
    }.collectAsState(initial = FeedView.FeedList)
    val movies = remember(currentMovieListFlowUseCase, moviesPagingDataUseCase, localeInteractor) {
        currentMovieListFlowUseCase(Unit)
            .distinctUntilChanged()
            .flatMapLatest { movieList ->
                moviesPagingDataUseCase(MoviesPagingDataUseCase.Params(movieList, localeInteractor.language))
            }
    }.collectAsLazyPagingItems()

    val lazyStaggeredGridState = rememberLazyStaggeredGridState()

    LaunchedEffect(Unit) {
        MainWebTabsEventManager.reselectFlow.collect { destination ->
            when (destination) {
                MainWebDestination.Feed -> {
                    lazyStaggeredGridState.animateScrollToItem(0)
                }
                else -> Unit
            }
        }
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.primaryContainer
    ) {
        when {
            movies.isFailure -> {
                PageFailure(
                    message = movies.refreshThrowable.toUserMessage(),
                    onRetryClick = movies::retry,
                    modifier = Modifier.fillMaxSize()
                )
            }
            movies.itemCount == 0 -> {
                PageLoading(
                    feedView = feedView,
                    modifier = Modifier.fillMaxSize()
                )
            }
            else -> {
                PageContent(
                    movies = movies,
                    feedView = feedView,
                    onMovieClick = onMovieClick,
                    lazyStaggeredGridState = lazyStaggeredGridState,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}
