@file:OptIn(ExperimentalCoroutinesApi::class)

package org.michaelbel.movies.feed

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onPreviewKeyEvent
import androidx.compose.ui.input.key.type
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.paging.compose.collectAsLazyPagingItems
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import org.koin.compose.koinInject
import org.michaelbel.movies.common.appearance.FeedView
import org.michaelbel.movies.common.exceptions.PageEmptyException
import org.michaelbel.movies.domain.usecase.CurrentFeedViewFlowUseCase
import org.michaelbel.movies.domain.usecase.CurrentMovieListFlowUseCase
import org.michaelbel.movies.domain.usecase.InsertMovieUseCase
import org.michaelbel.movies.domain.usecase.MovieUseCase
import org.michaelbel.movies.domain.usecase.MoviesFlowUseCase
import org.michaelbel.movies.domain.usecase.MoviesPagingDataUseCase
import org.michaelbel.movies.domain.usecase.RemoveMovieUseCase
import org.michaelbel.movies.domain.usecase.RemoveMoviesUseCase
import org.michaelbel.movies.domain.usecase.SearchMoviesPagingDataUseCase
import org.michaelbel.movies.domain.usecase.SuggestionPojosFlowUseCase
import org.michaelbel.movies.domain.usecase.UpdateSuggestionsUseCase
import org.michaelbel.movies.feed.ktx.toUserMessage
import org.michaelbel.movies.feed.ui.FeedSearchBar
import org.michaelbel.movies.interactor.LocaleInteractor
import org.michaelbel.movies.main.MainWebDestination
import org.michaelbel.movies.main.tabs.event.MainWebTabsEventManager
import org.michaelbel.movies.persistence.database.entity.pojo.MoviePojo
import org.michaelbel.movies.persistence.database.typealiases.MovieId
import org.michaelbel.movies.persistence.database.typealiases.PagingKey
import org.michaelbel.movies.ui.compose.page.PageContent
import org.michaelbel.movies.ui.compose.page.PageFailure
import org.michaelbel.movies.ui.compose.page.PageLoading
import org.michaelbel.movies.ui.isFailure
import org.michaelbel.movies.ui.isLoading
import org.michaelbel.movies.ui.refreshThrowable

@Composable
fun FeedScreen(
    onMovieClick: (PagingKey, MovieId) -> Unit = { _, _ -> },
    localeInteractor: LocaleInteractor = koinInject(),
    currentFeedViewFlowUseCase: CurrentFeedViewFlowUseCase = koinInject(),
    currentMovieListFlowUseCase: CurrentMovieListFlowUseCase = koinInject(),
    moviesPagingDataUseCase: MoviesPagingDataUseCase = koinInject(),
    searchMoviesPagingDataUseCase: SearchMoviesPagingDataUseCase = koinInject(),
    suggestionPojosFlowUseCase: SuggestionPojosFlowUseCase = koinInject(),
    moviesFlowUseCase: MoviesFlowUseCase = koinInject(),
    updateSuggestionsUseCase: UpdateSuggestionsUseCase = koinInject(),
    movieUseCase: MovieUseCase = koinInject(),
    insertMovieUseCase: InsertMovieUseCase = koinInject(),
    removeMovieUseCase: RemoveMovieUseCase = koinInject(),
    removeMoviesUseCase: RemoveMoviesUseCase = koinInject()
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
    val suggestionPojos by remember(suggestionPojosFlowUseCase) {
        suggestionPojosFlowUseCase(Unit)
    }.collectAsState(initial = emptyList())
    val searchHistoryMoviePojos by remember(moviesFlowUseCase) {
        moviesFlowUseCase(
            MoviesFlowUseCase.Params(
                pagingKey = MoviePojo.MOVIES_SEARCH_HISTORY,
                limit = Int.MAX_VALUE
            )
        )
    }.collectAsState(initial = emptyList())

    val scope = rememberCoroutineScope()
    val lazyStaggeredGridState = rememberLazyStaggeredGridState()
    var query by remember { mutableStateOf("") }
    var searchQuery by remember { mutableStateOf("") }
    var isSearchActive by remember { mutableStateOf(false) }
    var isSearchAutoFocusEnabled by remember { mutableStateOf(true) }

    val searchPagingItems = remember(searchMoviesPagingDataUseCase, localeInteractor) {
        snapshotFlow { searchQuery }
            .distinctUntilChanged()
            .flatMapLatest { text ->
                searchMoviesPagingDataUseCase(SearchMoviesPagingDataUseCase.Params(text, localeInteractor.language))
            }
    }.collectAsLazyPagingItems()

    fun clearSearchState() {
        query = ""
        searchQuery = ""
        isSearchActive = false
        isSearchAutoFocusEnabled = true
    }

    LaunchedEffect(Unit) {
        updateSuggestionsUseCase(localeInteractor.language)
    }

    LaunchedEffect(Unit) {
        MainWebTabsEventManager.reselectFlow.collect { destination ->
            when (destination) {
                MainWebDestination.Feed -> {
                    when {
                        isSearchActive || searchQuery.isNotEmpty() -> clearSearchState()
                        else -> lazyStaggeredGridState.animateScrollToItem(0)
                    }
                }
                else -> Unit
            }
        }
    }

    val searchBarHorizontalPadding: Dp by animateDpAsState(
        targetValue = if (isSearchActive) 0.dp else 16.dp,
        label = ""
    )
    val isSearchFailure = searchPagingItems.isFailure
    val isSearchEmptyFailure = isSearchFailure && searchPagingItems.refreshThrowable is PageEmptyException
    val searchFailureMessage = when {
        isSearchFailure -> searchPagingItems.refreshThrowable.toUserMessage()
        else -> ""
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .onPreviewKeyEvent { event ->
                when {
                    event.key == Key.Escape && event.type == KeyEventType.KeyDown && (isSearchActive || searchQuery.isNotBlank()) -> {
                        clearSearchState()
                        true
                    }
                    else -> false
                }
            },
        topBar = {
            FeedSearchBar(
                query = query,
                onQueryChange = { text -> query = text },
                onSearch = { text ->
                    query = text
                    searchQuery = text
                    isSearchActive = true
                    isSearchAutoFocusEnabled = true
                },
                active = isSearchActive,
                isAutoFocusEnabled = isSearchAutoFocusEnabled,
                isSearchResultsVisible = searchQuery.isNotBlank(),
                onActiveChange = { isActive ->
                    when {
                        !isActive && (query.isNotBlank() || searchQuery.isNotBlank()) -> clearSearchState()
                        else -> isSearchActive = isActive
                    }
                    if (isActive) {
                        isSearchAutoFocusEnabled = true
                    }
                },
                onBackClick = ::clearSearchState,
                onCloseClick = {
                    query = ""
                    searchQuery = ""
                    isSearchActive = true
                    isSearchAutoFocusEnabled = true
                },
                onInputText = { text ->
                    query = text
                    searchQuery = text
                    isSearchActive = true
                    isSearchAutoFocusEnabled = true
                },
                searchHistoryMovies = searchHistoryMoviePojos,
                suggestions = suggestionPojos,
                onClearSearchHistoryClick = {
                    scope.launch {
                        removeMoviesUseCase(MoviePojo.MOVIES_SEARCH_HISTORY)
                    }
                },
                onRemoveMovieFromHistoryClick = { movieId ->
                    scope.launch {
                        removeMovieUseCase(
                            RemoveMovieUseCase.Params(
                                pagingKey = MoviePojo.MOVIES_SEARCH_HISTORY,
                                movieId = movieId
                            )
                        )
                    }
                },
                isSearchRefreshLoading = searchPagingItems.isLoading,
                isSearchFailure = isSearchFailure,
                isSearchEmptyFailure = isSearchEmptyFailure,
                searchFailureMessage = searchFailureMessage,
                onSearchRetryClick = searchPagingItems::retry,
                searchLoadingContent = { modifier ->
                    PageLoading(
                        feedView = feedView,
                        modifier = modifier
                    )
                },
                searchContent = { modifier, searchLazyStaggeredGridState ->
                    PageContent(
                        movies = searchPagingItems,
                        feedView = feedView,
                        onMovieClick = { _, movieId ->
                            isSearchAutoFocusEnabled = false
                            scope.launch {
                                movieUseCase(
                                    MovieUseCase.Params(
                                        pagingKey = searchQuery,
                                        movieId = movieId
                                    )
                                ).onSuccess { movie ->
                                    insertMovieUseCase(
                                        InsertMovieUseCase.Params(
                                            pagingKey = MoviePojo.MOVIES_SEARCH_HISTORY,
                                            movie = movie
                                        )
                                    )
                                }
                            }
                            onMovieClick(searchQuery, movieId)
                        },
                        lazyStaggeredGridState = searchLazyStaggeredGridState,
                        modifier = modifier,
                        cardColor = MaterialTheme.colorScheme.primaryContainer
                    )
                },
                modifier = Modifier
                    .padding(horizontal = searchBarHorizontalPadding)
                    .fillMaxWidth()
            )
        },
        containerColor = MaterialTheme.colorScheme.primaryContainer
    ) { innerPadding ->
        when {
            movies.isFailure -> {
                PageFailure(
                    message = movies.refreshThrowable.toUserMessage(),
                    onRetryClick = movies::retry,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                )
            }
            movies.itemCount == 0 -> {
                PageLoading(
                    feedView = feedView,
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = innerPadding
                )
            }
            else -> {
                PageContent(
                    movies = movies,
                    feedView = feedView,
                    onMovieClick = onMovieClick,
                    lazyStaggeredGridState = lazyStaggeredGridState,
                    contentPadding = innerPadding,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}
