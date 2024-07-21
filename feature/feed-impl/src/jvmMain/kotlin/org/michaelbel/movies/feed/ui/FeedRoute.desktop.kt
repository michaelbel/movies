package org.michaelbel.movies.feed.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import org.koin.compose.koinInject
import org.michaelbel.movies.feed.FeedViewModel
import org.michaelbel.movies.ui.ktx.collectAsStateCommon

@Composable
fun FeedRoute(
    onNavigateToSearch: () -> Unit,
    onNavigateToAuth: () -> Unit,
    onNavigateToAccount: () -> Unit,
    onNavigateToSettings: () -> Unit,
    onNavigateToDetails: (String, Int) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: FeedViewModel = koinInject<FeedViewModel>()
) {
    val currentFeedView by viewModel.currentFeedView.collectAsStateCommon()
    val currentMovieList by viewModel.currentMovieList.collectAsStateCommon()
    val pagingData by viewModel.pagingDataFlow.collectAsStateCommon()

    FeedScreenContent(
        currentFeedView = currentFeedView,
        currentMovieList = currentMovieList,
        pagingItems = pagingData,
        onNavigateToSearch = onNavigateToSearch,
        onNavigateToAuth = onNavigateToAuth,
        onNavigateToAccount = onNavigateToAccount,
        onNavigateToSettings = onNavigateToSettings,
        onNavigateToDetails = onNavigateToDetails,
        modifier = modifier
    )
}