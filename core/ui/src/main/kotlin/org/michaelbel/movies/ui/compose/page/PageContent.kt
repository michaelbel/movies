package org.michaelbel.movies.ui.compose.page

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.staggeredgrid.LazyStaggeredGridState
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import org.michaelbel.movies.common.appearance.FeedView
import org.michaelbel.movies.network.isTmdbApiKeyEmpty
import org.michaelbel.movies.persistence.database.entity.MovieDb
import org.michaelbel.movies.ui.compose.ApiKeyBox
import org.michaelbel.movies.ui.compose.movie.MovieColumn
import org.michaelbel.movies.ui.compose.movie.MovieRow
import org.michaelbel.movies.ui.ktx.gridColumnsCount
import org.michaelbel.movies.ui.ktx.isNotEmpty
import org.michaelbel.movies.ui.ktx.isPagingFailure
import org.michaelbel.movies.ui.ktx.isPagingLoading
import org.michaelbel.movies.ui.ktx.isPortrait

@Composable
fun PageContent(
    feedView: FeedView,
    lazyListState: LazyListState,
    lazyGridState: LazyGridState,
    lazyStaggeredGridState: LazyStaggeredGridState,
    pagingItems: LazyPagingItems<MovieDb>,
    onMovieClick: (String, Int) -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues()
) {
    when (feedView) {
        is FeedView.FeedList -> {
            if (isPortrait) {
                PageContentColumn(
                    lazyListState = lazyListState,
                    pagingItems = pagingItems,
                    onMovieClick = onMovieClick,
                    contentPadding = contentPadding,
                    modifier = modifier
                )
            } else {
                PageContentGrid(
                    lazyGridState = lazyGridState,
                    pagingItems = pagingItems,
                    onMovieClick = onMovieClick,
                    contentPadding = contentPadding,
                    modifier = modifier
                )
            }
        }
        is FeedView.FeedGrid -> {
            PageContentStaggeredGrid(
                lazyStaggeredGridState = lazyStaggeredGridState,
                pagingItems = pagingItems,
                onMovieClick = onMovieClick,
                contentPadding = contentPadding,
                modifier = modifier
            )
        }
    }
}

@Composable
private fun PageContentColumn(
    lazyListState: LazyListState,
    pagingItems: LazyPagingItems<MovieDb>,
    onMovieClick: (String, Int) -> Unit,
    contentPadding: PaddingValues,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier.padding(top = 4.dp),
        state = lazyListState,
        contentPadding = contentPadding
    ) {
        items(
            count = pagingItems.itemCount,
            key = pagingItems.itemKey(),
            contentType = pagingItems.itemContentType()
        ) { index ->
            val movieDb: MovieDb? = pagingItems[index]
            if (movieDb != null) {
                MovieRow(
                    movie = movieDb,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                        .clip(MaterialTheme.shapes.small)
                        .background(MaterialTheme.colorScheme.inversePrimary)
                        .clickable { onMovieClick(movieDb.movieList, movieDb.movieId) }
                )
            }
        }
        if (isTmdbApiKeyEmpty && pagingItems.isNotEmpty) {
            item {
                ApiKeyBox(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp)
                )
            }
        }
        pagingItems.apply {
            when {
                isPagingLoading -> {
                    item {
                        PagingLoadingBox(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(80.dp)
                        )
                    }
                }
                isPagingFailure -> {
                    item {
                        PagingFailureBox(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(80.dp)
                                .padding(start = 8.dp, top = 4.dp, end = 8.dp)
                                .clip(MaterialTheme.shapes.small)
                                .clickable { retry() }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun PageContentGrid(
    lazyGridState: LazyGridState,
    pagingItems: LazyPagingItems<MovieDb>,
    onMovieClick: (String, Int) -> Unit,
    contentPadding: PaddingValues,
    modifier: Modifier = Modifier
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = modifier.padding(start = 8.dp, top = 4.dp, end = 8.dp),
        state = lazyGridState,
        contentPadding = contentPadding,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(
            count = pagingItems.itemCount,
            key = pagingItems.itemKey(),
            contentType = pagingItems.itemContentType()
        ) { index ->
            val movieDb: MovieDb? = pagingItems[index]
            if (movieDb != null) {
                MovieRow(
                    movie = movieDb,
                    maxLines = 1,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                        .clip(MaterialTheme.shapes.small)
                        .background(MaterialTheme.colorScheme.inversePrimary)
                        .clickable { onMovieClick(movieDb.movieList, movieDb.movieId) }
                )
            }
        }
        pagingItems.apply {
            when {
                isPagingLoading -> {
                    item {
                        PagingLoadingBox(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(80.dp)

                        )
                    }
                }
                isPagingFailure -> {
                    item {
                        PagingFailureBox(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(80.dp)
                                .clip(MaterialTheme.shapes.small)
                                .clickable { retry() }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun PageContentStaggeredGrid(
    lazyStaggeredGridState: LazyStaggeredGridState,
    pagingItems: LazyPagingItems<MovieDb>,
    onMovieClick: (String, Int) -> Unit,
    contentPadding: PaddingValues,
    modifier: Modifier = Modifier
) {
    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Fixed(gridColumnsCount),
        modifier = modifier.padding(start = 8.dp, top = 8.dp, end = 8.dp),
        state = lazyStaggeredGridState,
        contentPadding = contentPadding,
        verticalItemSpacing = 8.dp,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(
            count = pagingItems.itemCount,
            key = pagingItems.itemKey(),
            contentType = pagingItems.itemContentType()
        ) { index ->
            val movieDb: MovieDb? = pagingItems[index]
            if (movieDb != null) {
                MovieColumn(
                    movie = movieDb,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(MaterialTheme.shapes.small)
                        .background(MaterialTheme.colorScheme.inversePrimary)
                        .clickable { onMovieClick(movieDb.movieList, movieDb.movieId) }
                )
            }
        }
        pagingItems.apply {
            when {
                isPagingLoading -> {
                    item {
                        PagingLoadingBox(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(80.dp)

                        )
                    }
                }
                isPagingFailure -> {
                    item {
                        PagingFailureBox(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(80.dp)
                                .clip(MaterialTheme.shapes.small)
                                .clickable { retry() }
                        )
                    }
                }
            }
        }
    }
}