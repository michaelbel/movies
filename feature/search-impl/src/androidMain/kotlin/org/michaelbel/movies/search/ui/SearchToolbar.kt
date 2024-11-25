@file:OptIn(ExperimentalMaterial3Api::class)

package org.michaelbel.movies.search.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import org.michaelbel.movies.persistence.database.entity.pojo.MoviePojo
import org.michaelbel.movies.persistence.database.entity.pojo.SuggestionPojo
import org.michaelbel.movies.persistence.database.typealiases.MovieId
import org.michaelbel.movies.persistence.database.typealiases.Query
import org.michaelbel.movies.search_impl.R
import org.michaelbel.movies.ui.compose.iconbutton.BackIcon
import org.michaelbel.movies.ui.compose.iconbutton.CloseIcon
import org.michaelbel.movies.ui.compose.iconbutton.VoiceIcon
import org.michaelbel.movies.ui.ktx.rememberSpeechRecognitionLauncher

@Composable
internal fun SearchToolbar(
    query: String,
    onQueryChange: (Query) -> Unit,
    onSearch: (Query) -> Unit,
    active: Boolean,
    onActiveChange: (Boolean) -> Unit,
    onBackClick: () -> Unit,
    onCloseClick: () -> Unit,
    onInputText: (Query) -> Unit,
    suggestions: List<SuggestionPojo>,
    searchHistoryMovies: List<MoviePojo>,
    onHistoryMovieRemoveClick: (MovieId) -> Unit,
    onClearHistoryClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    SearchBar(
        query = query,
        onQueryChange = onQueryChange,
        onSearch = onSearch,
        active = active,
        onActiveChange = onActiveChange,
        modifier = modifier,
        placeholder = {
            Text(
                text = stringResource(R.string.search_title)
            )
        },
        leadingIcon = {
            BackIcon(
                onClick = onBackClick
            )
        },
        trailingIcon = {
            if (query.isNotEmpty()) {
                CloseIcon(
                    onClick = onCloseClick
                )
            } else {
                VoiceIcon(
                    onClick = rememberSpeechRecognitionLauncher(onInputText)
                )
            }
        },
        colors = SearchBarDefaults.colors(
            containerColor = MaterialTheme.colorScheme.inversePrimary,
            dividerColor = MaterialTheme.colorScheme.onPrimaryContainer
        )
    ) {
        when {
            searchHistoryMovies.isNotEmpty() -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .imePadding()
                ) {
                    SearchHistoryHeader(
                        onClearButtonClick = onClearHistoryClick,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp)
                    )

                    LazyColumn {
                        items(
                            items = searchHistoryMovies,
                            key = { it.movieId }
                        ) { movieDb ->
                            SwipeToDismiss(
                                item = movieDb,
                                onDelete = { deletedMovieDb ->
                                    onHistoryMovieRemoveClick(deletedMovieDb.movieId)
                                }
                            ) { swipedMovieDb, onDelete ->
                                SearchRecentResult(
                                    text = swipedMovieDb.title,
                                    onRemoveClick = { onDelete(swipedMovieDb) },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(52.dp)
                                        .background(MaterialTheme.colorScheme.inversePrimary)
                                        .clickable { onInputText(swipedMovieDb.title) }
                                )
                            }
                        }
                    }
                }
            }
            suggestions.isNotEmpty() -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .imePadding(),
                    contentAlignment = Alignment.Center
                ) {
                    LazyColumn {
                        items(suggestions) { suggestion ->
                            SearchSuggestion(
                                text = suggestion.title,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(52.dp)
                                    .clickable { onInputText(suggestion.title) }
                            )
                        }
                    }
                }
            }
            else -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .imePadding(),
                    contentAlignment = Alignment.Center
                ) {
                    SearchEmpty()
                }
            }
        }
    }
}