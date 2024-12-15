package org.michaelbel.movies.search.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.paging.compose.collectAsLazyPagingItems
import org.michaelbel.movies.persistence.database.typealiases.MovieId
import org.michaelbel.movies.persistence.database.typealiases.PagingKey
import org.michaelbel.movies.search.SearchViewModel2
import org.michaelbel.movies.ui.ktx.collectAsStateCommon

@Composable
actual fun SearchRoute(
    onBackClick: () -> Unit,
    onNavigateToDetails: (PagingKey, MovieId) -> Unit,
    modifier: Modifier,
    viewModel: SearchViewModel2
) {
    val pagingItems = viewModel.pagingDataFlow.collectAsLazyPagingItems()
    val currentFeedView by viewModel.currentFeedView.collectAsStateCommon()
    val networkStatus by viewModel.networkStatus.collectAsStateCommon()
    val suggestions by viewModel.suggestionsFlow.collectAsStateCommon()
    val searchHistoryMovies by viewModel.searchHistoryMoviesFlow.collectAsStateCommon()
    val active by viewModel.isSearchActive.collectAsStateCommon()

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