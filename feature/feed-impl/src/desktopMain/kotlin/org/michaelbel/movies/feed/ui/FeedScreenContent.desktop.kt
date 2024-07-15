@file:OptIn(ExperimentalMaterial3Api::class)

package org.michaelbel.movies.feed.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.michaelbel.movies.common.appearance.FeedView
import org.michaelbel.movies.common.list.MovieList
import org.michaelbel.movies.feed.ktx.titleText
import org.michaelbel.movies.persistence.database.entity.pojo.AccountPojo
import org.michaelbel.movies.persistence.database.entity.pojo.MoviePojo
import org.michaelbel.movies.ui.compose.page.PageContent
import org.michaelbel.movies.ui.compose.page.PageLoading

@Composable
internal fun FeedScreenContent(
    currentFeedView: FeedView,
    currentMovieList: MovieList,
    pagingItems: List<MoviePojo>,
    onNavigateToSearch: () -> Unit,
    onNavigateToAuth: () -> Unit,
    onNavigateToAccount: () -> Unit,
    onNavigateToSettings: () -> Unit,
    onNavigateToDetails: (String, Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            FeedToolbar(
                title = currentMovieList.titleText,
                account = AccountPojo.Empty,
                isTmdbApiKeyEmpty = false,
                topAppBarScrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(),
                onSearchIconClick = onNavigateToSearch,
                onAuthIconClick = onNavigateToAuth,
                onAccountIconClick = onNavigateToAccount,
                onSettingsIconClick = onNavigateToSettings
            )
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { innerPadding ->
        when {
            pagingItems.isEmpty() -> {
                PageLoading(
                    feedView = currentFeedView,
                    paddingValues = innerPadding
                )
            }
            else -> {
                PageContent(
                    feedView = currentFeedView,
                    lazyListState = rememberLazyListState(),
                    lazyGridState = rememberLazyGridState(),
                    lazyStaggeredGridState = rememberLazyStaggeredGridState(),
                    pagingItems = pagingItems,
                    onMovieClick = onNavigateToDetails,
                    contentPadding = innerPadding,
                    modifier = Modifier
                )
            }
        }
    }
}