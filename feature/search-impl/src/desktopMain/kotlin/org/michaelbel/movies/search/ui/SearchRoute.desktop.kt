package org.michaelbel.movies.search.ui

import androidx.compose.foundation.clickable
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.koin.compose.koinInject
import org.michaelbel.movies.persistence.database.typealiases.MovieId
import org.michaelbel.movies.persistence.database.typealiases.PagingKey
import org.michaelbel.movies.search.SearchViewModel

@Composable
fun SearchRoute(
    onBackClick: () -> Unit,
    onNavigateToDetails: (PagingKey, MovieId) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SearchViewModel = koinInject<SearchViewModel>()
) {
    Text(
        text = "Feed",
        modifier = Modifier.clickable { onBackClick() }
    )
}