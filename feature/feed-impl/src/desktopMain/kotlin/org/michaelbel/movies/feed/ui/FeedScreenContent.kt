@file:OptIn(ExperimentalMaterial3Api::class)

package org.michaelbel.movies.feed.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.michaelbel.movies.common.list.MovieList
import org.michaelbel.movies.feed.ktx.titleText
import org.michaelbel.movies.persistence.database.entity.pojo.AccountPojo

@Composable
internal fun FeedScreenContent(
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

    }
}