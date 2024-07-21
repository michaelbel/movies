package org.michaelbel.movies.feed.ui

import androidx.compose.foundation.clickable
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun FeedRoute(
    onNavigateToSearch: () -> Unit,
    onNavigateToAuth: () -> Unit,
    onNavigateToAccount: () -> Unit,
    onNavigateToSettings: () -> Unit,
    onNavigateToDetails: (String, Int) -> Unit,
    modifier: Modifier = Modifier,
    //viewModel: FeedViewModel = koinInject<FeedViewModel>()
) {
    Text(
        text = "settings",
        modifier = Modifier.clickable { onNavigateToSettings() }
    )

    /*val currentFeedView by viewModel.currentFeedView.collectAsStateCommon()
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
    )*/
}