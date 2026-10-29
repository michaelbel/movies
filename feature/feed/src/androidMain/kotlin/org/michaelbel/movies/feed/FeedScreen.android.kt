package org.michaelbel.movies.feed

import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyStaggeredGridState
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.stringResource
import org.michaelbel.movies.common.exceptions.ApiKeyNotNullException
import org.michaelbel.movies.common.exceptions.PageEmptyException
import org.michaelbel.movies.feed.event.FeedEvent
import org.michaelbel.movies.feed.event.FeedEventManager
import org.michaelbel.movies.feed.intent.FeedIntent
import org.michaelbel.movies.feed.model.FeedModel
import org.michaelbel.movies.feed.ui.FeedSearchBar
import org.michaelbel.movies.network.config.isTmdbApiKeyEmpty
import org.michaelbel.movies.network.connectivity.NetworkStatus
import org.michaelbel.movies.persistence.database.entity.pojo.MoviePojo
import org.michaelbel.movies.ui.ObserveAsEvents
import org.michaelbel.movies.ui.collectAsStateCommon
import org.michaelbel.movies.ui.compose.page.FeedEmpty
import org.michaelbel.movies.ui.compose.page.PageContent
import org.michaelbel.movies.ui.compose.page.PageFailure
import org.michaelbel.movies.ui.compose.page.PageLoading
import org.michaelbel.movies.ui.isFailure
import org.michaelbel.movies.ui.isLoading
import org.michaelbel.movies.ui.isNavigationRail
import org.michaelbel.movies.ui.refreshThrowable
import org.michaelbel.movies.ui.strings.MoviesStrings
import java.net.UnknownHostException

@Composable
actual fun FeedScreen(
    viewModel: FeedViewModel,
    initialSearchActive: Boolean,
    onSearchActiveChange: (Boolean) -> Unit
) {
    val state by viewModel.stateFlow.collectAsStateCommon()

    val pagingItems = viewModel.pagingDataFlow.collectAsLazyPagingItems()
    val searchPagingItems = viewModel.searchPagingDataFlow.collectAsLazyPagingItems()
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    val feedLazyStaggeredGridState = rememberLazyStaggeredGridState()

    FeedScreenContent(
        state = state,
        dispatch = viewModel::dispatch,
        pagingItems = pagingItems,
        searchPagingItems = searchPagingItems,
        snackbarHostState = snackbarHostState,
        feedLazyStaggeredGridState = feedLazyStaggeredGridState,
        initialSearchActive = initialSearchActive,
        onSearchActiveChange = onSearchActiveChange
    )

    ObserveAsEvents(
        flow = viewModel.eventFlow,
        key1 = snackbarHostState
    ) { event ->
        when (event) {
            is FeedEvent.ScrollToTop -> {
                scope.launch { feedLazyStaggeredGridState.animateScrollToItem(0) }
            }
            is FeedEvent.ShowSnackbar -> {
                snackbarHostState.currentSnackbarData?.dismiss()
                scope.launch {
                    snackbarHostState.run {
                        showSnackbar(
                            message = event.message,
                            duration = if (event.isLong) SnackbarDuration.Long else SnackbarDuration.Short
                        )
                    }
                }
            }
            else -> Unit
        }
    }
}

@Composable
private fun FeedScreenContent(
    state: FeedModel,
    dispatch: (FeedIntent) -> Unit,
    pagingItems: LazyPagingItems<MoviePojo>,
    searchPagingItems: LazyPagingItems<MoviePojo>,
    snackbarHostState: SnackbarHostState,
    feedLazyStaggeredGridState: LazyStaggeredGridState,
    initialSearchActive: Boolean,
    onSearchActiveChange: (Boolean) -> Unit
) {
    var query by rememberSaveable { mutableStateOf("") }
    var searchQuery by rememberSaveable { mutableStateOf("") }
    var isSearchActive by rememberSaveable { mutableStateOf(initialSearchActive) }
    var isSearchAutoFocusEnabled by rememberSaveable { mutableStateOf(true) }
    val contentBottomPadding = when {
        isSearchActive -> 16.dp
        isNavigationRail -> 0.dp
        else -> 80.dp
    }

    LaunchedEffect(isSearchActive, onSearchActiveChange) {
        onSearchActiveChange(isSearchActive)
    }

    DisposableEffect(onSearchActiveChange) {
        onDispose { onSearchActiveChange(false) }
    }

    fun clearSearchState() {
        query = ""
        searchQuery = ""
        dispatch(FeedIntent.EnterSearchQuery(searchQuery))
        isSearchActive = false
        isSearchAutoFocusEnabled = true
    }

    BackHandler(
        enabled = isSearchActive || searchQuery.isNotBlank()
    ) {
        clearSearchState()
    }

    ObserveAsEvents(
        flow = FeedEventManager.eventFlow
    ) { event ->
        when (event) {
            FeedEvent.ReselectFeed -> {
                when {
                    isSearchActive || searchQuery.isNotEmpty() -> clearSearchState()
                    else -> dispatch(FeedIntent.ScrollToTop)
                }
            }
            FeedEvent.OpenSearch -> {
                isSearchActive = true
                isSearchAutoFocusEnabled = true
            }
            else -> Unit
        }
    }

    if (pagingItems.isFailure && pagingItems.refreshThrowable is ApiKeyNotNullException) {
        dispatch(FeedIntent.ShowSnackbar(stringResource(MoviesStrings.error_api_key_null), true))
    }

    if (state.networkStatus == NetworkStatus.Available && pagingItems.isFailure && pagingItems.refreshThrowable is UnknownHostException) {
        pagingItems.retry()
    }

    val searchBarHorizontalPadding: Dp by animateDpAsState(
        targetValue = if (isSearchActive) 0.dp else 16.dp,
        label = ""
    )
    val isSearchFailure = searchPagingItems.isFailure
    val isSearchEmptyFailure = isSearchFailure && searchPagingItems.refreshThrowable is PageEmptyException

    Scaffold(
        topBar = {
            Column {
                FeedSearchBar(
                    query = query,
                    onQueryChange = { text -> query = text },
                    onSearch = { text ->
                        query = text
                        searchQuery = text
                        dispatch(FeedIntent.EnterSearchQuery(searchQuery))
                        isSearchActive = true
                        isSearchAutoFocusEnabled = true
                    },
                    active = isSearchActive,
                    isAutoFocusEnabled = isSearchAutoFocusEnabled,
                    isSearchResultsVisible = searchQuery.isNotBlank(),
                    onActiveChange = {
                        when {
                            !it && (query.isNotBlank() || searchQuery.isNotBlank()) -> clearSearchState()
                            else -> isSearchActive = it
                        }
                        if (it) {
                            isSearchAutoFocusEnabled = true
                        }
                    },
                    onBackClick = ::clearSearchState,
                    onCloseClick = {
                        query = ""
                        searchQuery = ""
                        dispatch(FeedIntent.EnterSearchQuery(searchQuery))
                        isSearchActive = true
                        isSearchAutoFocusEnabled = true
                    },
                    onInputText = { text ->
                        query = text
                        searchQuery = text
                        dispatch(FeedIntent.EnterSearchQuery(searchQuery))
                        isSearchActive = true
                        isSearchAutoFocusEnabled = true
                    },
                    state = state,
                    dispatch = dispatch,
                    isSearchRefreshLoading = searchPagingItems.isLoading,
                    isSearchFailure = isSearchFailure,
                    isSearchEmptyFailure = isSearchEmptyFailure,
                    onSearchRetryClick = searchPagingItems::retry,
                    searchLoadingContent = { modifier ->
                        PageLoading(
                            feedView = state.feedView,
                            modifier = modifier,
                            cardColor = MaterialTheme.colorScheme.primaryContainer
                        )
                    },
                    searchContent = { modifier, lazyStaggeredGridState ->
                        PageContent(
                            feedView = state.feedView,
                            lazyStaggeredGridState = lazyStaggeredGridState,
                            pagingItems = searchPagingItems,
                            onMovieClick = { _, movieId ->
                                isSearchAutoFocusEnabled = false
                                dispatch(FeedIntent.SaveMovieToSearchHistoryClick(movieId))
                                dispatch(FeedIntent.MovieDetailsClick(searchQuery, movieId))
                            },
                            modifier = modifier,
                            contentPadding = PaddingValues(bottom = contentBottomPadding),
                            cardColor = MaterialTheme.colorScheme.primaryContainer
                        )
                    },
                    modifier = Modifier
                        .padding(horizontal = searchBarHorizontalPadding)
                        .fillMaxWidth()
                )

                if (isTmdbApiKeyEmpty) {
                    Text(
                        text = stringResource(MoviesStrings.error_api_key_null),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 16.dp),
                        style = MaterialTheme.typography.bodyLarge.copy(
                            color = MaterialTheme.colorScheme.error,
                            textAlign = TextAlign.Center
                        )
                    )
                }
            }
        },
        snackbarHost = {
            SnackbarHost(
                hostState = snackbarHostState
            )
        },
        containerColor = MaterialTheme.colorScheme.primaryContainer
    ) { innerPadding ->
        when {
            pagingItems.isLoading -> {
                PageLoading(
                    feedView = state.feedView,
                    contentPadding = innerPadding
                )
            }
            pagingItems.isFailure -> {
                when {
                    pagingItems.refreshThrowable is PageEmptyException -> {
                        FeedEmpty(
                            contentPadding = innerPadding
                        )
                    }
                    else -> {
                        PageFailure(
                            onClick = pagingItems::retry,
                            contentPadding = innerPadding
                        )
                    }
                }
            }
            else -> {
                PageContent(
                    feedView = state.feedView,
                    lazyStaggeredGridState = feedLazyStaggeredGridState,
                    pagingItems = pagingItems,
                    onMovieClick = { pagingKey, movieId ->
                        dispatch(FeedIntent.MovieDetailsClick(pagingKey, movieId))
                    },
                    contentPadding = innerPadding
                )
            }
        }
    }
}
