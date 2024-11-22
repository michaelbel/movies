@file:OptIn(ExperimentalMaterial3Api::class)

package org.michaelbel.movies.feed.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import org.michaelbel.movies.common.appearance.FeedView
import org.michaelbel.movies.common.list.MovieList
import org.michaelbel.movies.feed.ktx.titleText

@Composable
internal fun FeedScreenContent(
    currentFeedView: FeedView,
    currentMovieList: MovieList,
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
                isTmdbApiKeyEmpty = false,
                topAppBarScrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(),
                isSearchIconVisible = false,
                onSearchIconClick = onNavigateToSearch,
                isAuthIconVisible = false,
                onAuthIconClick = onNavigateToAuth,
                onAccountIconClick = onNavigateToAccount,
                onSettingsIconClick = onNavigateToSettings
            )
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { innerPadding ->
        Box(
            modifier = Modifier.fillMaxSize().background(Color.Red)
        )
    }
}