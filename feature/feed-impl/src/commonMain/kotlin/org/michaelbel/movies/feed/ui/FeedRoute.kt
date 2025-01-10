package org.michaelbel.movies.feed.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.koin.compose.viewmodel.koinViewModel
import org.michaelbel.movies.feed.FeedViewModel

@Composable
expect fun FeedRoute(
    onNavigateToSearch: () -> Unit,
    onNavigateToAuth: () -> Unit,
    onNavigateToAccount: () -> Unit,
    onNavigateToSettings: () -> Unit,
    onNavigateToDetails: (String, Int) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: FeedViewModel = koinViewModel()
)