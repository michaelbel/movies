package org.michaelbel.movies.feed.ui

import android.content.Context
import android.content.Intent
import android.os.Build
import android.provider.Settings
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.michaelbel.movies.domain.data.entity.AccountDb
import org.michaelbel.movies.domain.data.entity.MovieDb
import org.michaelbel.movies.domain.data.ktx.orEmpty
import org.michaelbel.movies.domain.exceptions.ApiKeyNotNullException
import org.michaelbel.movies.entities.isTmdbApiKeyEmpty
import org.michaelbel.movies.feed.FeedViewModel
import org.michaelbel.movies.feed_impl.R
import org.michaelbel.movies.network.connectivity.NetworkStatus
import org.michaelbel.movies.ui.ktx.clickableWithoutRipple
import org.michaelbel.movies.ui.ktx.isFailure
import org.michaelbel.movies.ui.ktx.isLoading
import org.michaelbel.movies.ui.ktx.throwable
import java.net.UnknownHostException

@Composable
fun FeedRoute(
    onNavigateToAccount: () -> Unit,
    onNavigateToAuth: () -> Unit,
    onNavigateToSettings: () -> Unit,
    onNavigateToDetails: (Int) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: FeedViewModel = hiltViewModel()
) {
    val pagingItems: LazyPagingItems<MovieDb> = viewModel.pagingItems.collectAsLazyPagingItems()
    val account: AccountDb? by viewModel.account.collectAsStateWithLifecycle()
    val isSettingsIconVisible: Boolean by viewModel.isSettingsIconVisible.collectAsStateWithLifecycle()
    val networkStatus: NetworkStatus by viewModel.networkStatus.collectAsStateWithLifecycle()

    FeedScreenContent(
        modifier = modifier,
        pagingItems = pagingItems,
        account = account.orEmpty,
        networkStatus = networkStatus,
        isSettingsIconVisible = isSettingsIconVisible,
        onNavigateToAuth = onNavigateToAuth,
        onNavigateToAccount = onNavigateToAccount,
        onNavigateToSettings = onNavigateToSettings,
        onNavigateToDetails = onNavigateToDetails
    )
}

@Composable
private fun FeedScreenContent(
    modifier: Modifier = Modifier,
    pagingItems: LazyPagingItems<MovieDb>,
    account: AccountDb,
    networkStatus: NetworkStatus,
    isSettingsIconVisible: Boolean,
    onNavigateToAuth: () -> Unit,
    onNavigateToAccount: () -> Unit,
    onNavigateToSettings: () -> Unit,
    onNavigateToDetails: (Int) -> Unit
) {
    val context: Context = LocalContext.current
    val scope: CoroutineScope = rememberCoroutineScope()
    val listState: LazyListState = rememberLazyListState()
    val snackbarHostState: SnackbarHostState = remember { SnackbarHostState() }

    val onScrollToTop: () -> Unit = {
        scope.launch {
            listState.animateScrollToItem(0)
        }
    }

    val onShowSnackbar: (String) -> Unit = { message ->
        scope.launch {
            snackbarHostState.showSnackbar(
                message = message,
                duration = SnackbarDuration.Long
            )
        }
    }

    if (pagingItems.isFailure && pagingItems.throwable is ApiKeyNotNullException) {
        onShowSnackbar(stringResource(R.string.feed_error_api_key_null))
    }

    if (networkStatus == NetworkStatus.Available && pagingItems.isFailure && pagingItems.throwable is UnknownHostException) {
        pagingItems.retry()
    }

    val resultContract = rememberLauncherForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {}

    Scaffold(
        modifier = modifier,
        topBar = {
            FeedToolbar(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickableWithoutRipple { onScrollToTop() },
                account = account,
                isSettingsIconVisible = isSettingsIconVisible,
                onAuthIconClick = {
                    if (isTmdbApiKeyEmpty) {
                        onShowSnackbar(context.getString(R.string.feed_error_api_key_null))
                    } else {
                        onNavigateToAuth()
                    }
                },
                onAccountIconClick = onNavigateToAccount,
                onSettingsIconClick = onNavigateToSettings
            )
        },
        snackbarHost = {
            SnackbarHost(
                hostState = snackbarHostState
            )
        },
        containerColor = MaterialTheme.colorScheme.primaryContainer
    ) { paddingValues: PaddingValues ->
        when {
            pagingItems.isLoading -> {
                FeedLoading(
                    modifier = Modifier
                        .fillMaxSize(),
                    paddingValues = paddingValues
                )
            }
            pagingItems.isFailure -> {
                FeedFailure(
                    modifier = Modifier
                        .padding(paddingValues)
                        .fillMaxSize()
                        .clickableWithoutRipple { pagingItems.retry() },
                    onCheckConnectivityClick = {
                        if (Build.VERSION.SDK_INT >= 29) {
                            resultContract.launch(
                                Intent(Settings.Panel.ACTION_INTERNET_CONNECTIVITY)
                            )
                        }
                    }
                )
            }
            else -> {
                FeedContent(
                    modifier = Modifier
                        .fillMaxSize(),
                    paddingValues = paddingValues,
                    listState = listState,
                    pagingItems = pagingItems,
                    onMovieClick = onNavigateToDetails
                )
            }
        }
    }
}