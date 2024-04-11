package org.michaelbel.movies.feed.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.collectAsLazyPagingItems
import org.koin.androidx.compose.koinViewModel
import org.michaelbel.movies.feed.FeedViewModel
import org.michaelbel.movies.persistence.database.ktx.orEmpty

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
    val account by viewModel.account.collectAsStateWithLifecycle()
    val currentFeedView by viewModel.currentFeedView.collectAsStateWithLifecycle()
    val currentMovieList by viewModel.currentMovieList.collectAsStateWithLifecycle()
    val notificationsPermissionRequired by viewModel.notificationsPermissionRequired.collectAsStateWithLifecycle()
    val networkStatus by viewModel.networkStatus.collectAsStateWithLifecycle()
    val isAuthFailureSnackbarShowed = viewModel.isAuthFailureSnackbarShowed

    FeedScreenContent(
        pagingItems = pagingItems,
        account = account.orEmpty,
        networkStatus = networkStatus,
        currentFeedView = currentFeedView,
        currentMovieList = currentMovieList,
        notificationsPermissionRequired = notificationsPermissionRequired,
        isAuthFailureSnackbarShowed = isAuthFailureSnackbarShowed,
        onNavigateToSearch = onNavigateToSearch,
        onNavigateToAuth = onNavigateToAuth,
        onNavigateToAccount = onNavigateToAccount,
        onNavigateToSettings = onNavigateToSettings,
        onNavigateToDetails = onNavigateToDetails,
        onNotificationBottomSheetHideClick = viewModel::onNotificationBottomSheetHide,
        onSnackbarDismissed = viewModel::onSnackbarDismissed,
        modifier = modifier
    )
}