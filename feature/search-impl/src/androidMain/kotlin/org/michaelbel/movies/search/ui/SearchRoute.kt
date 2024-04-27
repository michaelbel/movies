package org.michaelbel.movies.search.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.collectAsLazyPagingItems
import org.koin.androidx.compose.koinViewModel
import org.michaelbel.movies.persistence.database.typealiases.MovieId
import org.michaelbel.movies.persistence.database.typealiases.PagingKey
import org.michaelbel.movies.search.SearchViewModel

@Composable
fun SearchRoute(
    onBackClick: () -> Unit,
    onNavigateToDetails: (PagingKey, MovieId) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SearchViewModel = koinViewModel()
) {
    val pagingItems = viewModel.pagingDataFlow.collectAsLazyPagingItems()
    val currentFeedView by viewModel.currentFeedView.collectAsStateWithLifecycle()
    val networkStatus by viewModel.networkStatus.collectAsStateWithLifecycle()
    val suggestions by viewModel.suggestionsFlow.collectAsStateWithLifecycle()
    val searchHistoryMovies by viewModel.searchHistoryMoviesFlow.collectAsStateWithLifecycle()
    val active by viewModel.isSearchActive.collectAsStateWithLifecycle()

    SearchScreenContent(
        pagingItems = pagingItems,
        networkStatus = networkStatus,
        currentFeedView = currentFeedView,
        suggestions = suggestions,
        searchHistoryMovies = searchHistoryMovies,
        onBackClick = onBackClick,
        onNavigateToDetails = onNavigateToDetails,
        onChangeSearchQuery = viewModel::onChangeSearchQuery,
        onSaveMovieToHistory = viewModel::onSaveToHistory,
        onRemoveMovieFromHistory = viewModel::onRemoveFromHistory,
        onHistoryClear = viewModel::onClearSearchHistory,
        active = active,
        onChangeActiveState = viewModel::onChangeActiveState,
        modifier = modifier
    )
}