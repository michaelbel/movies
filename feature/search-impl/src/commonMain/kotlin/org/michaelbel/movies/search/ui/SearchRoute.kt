package org.michaelbel.movies.search.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.koin.compose.viewmodel.koinViewModel
import org.michaelbel.movies.persistence.database.typealiases.MovieId
import org.michaelbel.movies.persistence.database.typealiases.PagingKey
import org.michaelbel.movies.search.SearchViewModel2

@Composable
expect fun SearchRoute(
    onBackClick: () -> Unit,
    onNavigateToDetails: (PagingKey, MovieId) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SearchViewModel2 = koinViewModel()
)