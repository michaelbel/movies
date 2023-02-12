package org.michaelbel.movies.feed.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.items
import org.michaelbel.movies.entities.MovieData
import org.michaelbel.movies.feed.ktx.isPagingFailure
import org.michaelbel.movies.feed.ktx.isPagingLoading

@Composable
internal fun FeedContent(
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues = PaddingValues(),
    listState: LazyListState,
    pagingItems: LazyPagingItems<MovieData>,
    onMovieClick: (Int) -> Unit
) {
    LazyColumn(
        modifier = modifier,
        state = listState,
        contentPadding = paddingValues
    ) {
        items(pagingItems) { movieItem ->
            movieItem?.let { movie ->
                FeedMovieBox(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            start = 16.dp,
                            top = 4.dp,
                            end = 16.dp,
                            bottom = 4.dp
                        )
                        .clip(MaterialTheme.shapes.small)
                        .background(MaterialTheme.colorScheme.inversePrimary)
                        .clickable {
                            onMovieClick(movie.id)
                        },
                    movie = movie
                )
            }
        }
        pagingItems.apply {
            when {
                isPagingLoading -> {
                    item {
                        FeedLoadingBox(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(80.dp)
                        )
                    }
                }
                isPagingFailure -> {
                    item {
                        FeedErrorBox(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(80.dp)
                                .clickable { retry() }
                        )
                    }
                }
            }
        }
    }
}