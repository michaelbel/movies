package org.michaelbel.movies.feed.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.paging.compose.collectAsLazyPagingItems
import org.koin.androidx.compose.koinViewModel
import org.michaelbel.movies.feed.FeedViewModel
import org.michaelbel.movies.persistence.database.ktx.orEmpty
import org.michaelbel.movies.ui.ktx.collectAsStateCommon

@Composable
fun FeedRoute(
    onNavigateToSearch: () -> Unit,
    onNavigateToAuth: () -> Unit,
    onNavigateToAccount: () -> Unit,
    onNavigateToSettings: () -> Unit,
    onNavigateToDetails: (String, Int) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: FeedViewModel = koinViewModel()
) {
    val pagingItems = viewModel.pagingDataFlow.collectAsLazyPagingItems()
    val account by viewModel.account.collectAsStateCommon()
    val currentFeedView by viewModel.currentFeedView.collectAsStateCommon()
    val currentMovieList by viewModel.currentMovieList.collectAsStateCommon()
    val notificationsPermissionRequired by viewModel.notificationsPermissionRequired.collectAsStateCommon()
    val networkStatus by viewModel.networkStatus.collectAsStateCommon()

    FeedScreenContent(
        pagingItems = pagingItems,
        account = account.orEmpty,
        networkStatus = networkStatus,
        currentFeedView = currentFeedView,
        currentMovieList = currentMovieList,
        notificationsPermissionRequired = notificationsPermissionRequired,
        onNavigateToSearch = onNavigateToSearch,
        onNavigateToAuth = onNavigateToAuth,
        onNavigateToAccount = onNavigateToAccount,
        onNavigateToSettings = onNavigateToSettings,
        onNavigateToDetails = onNavigateToDetails,
        onNotificationBottomSheetHideClick = viewModel::onNotificationBottomSheetHide,
        modifier = modifier
    )
}