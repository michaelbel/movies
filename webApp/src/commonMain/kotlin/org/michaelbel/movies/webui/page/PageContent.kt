package org.michaelbel.movies.webui.page

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.staggeredgrid.LazyStaggeredGridState
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridItemSpan
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import org.michaelbel.movies.common.appearance.FeedView
import org.michaelbel.movies.network.config.formatBackdropImage
import org.michaelbel.movies.network.config.formatPosterImage
import org.michaelbel.movies.persistence.database.entity.pojo.MoviePojo
import org.michaelbel.movies.persistence.database.typealiases.MovieId
import org.michaelbel.movies.persistence.database.typealiases.PagingKey
import org.michaelbel.movies.ui.compose.page.PagingLoadingBox
import org.michaelbel.movies.ui.compose.plus
import org.michaelbel.movies.ui.isPagingFailure
import org.michaelbel.movies.ui.isPagingLoading
import org.michaelbel.movies.webui.MovieCard

@Composable
fun PageContent(
    movies: LazyPagingItems<MoviePojo>,
    feedView: FeedView,
    onMovieClick: (PagingKey, MovieId) -> Unit,
    lazyStaggeredGridState: LazyStaggeredGridState = rememberLazyStaggeredGridState(),
    contentPadding: PaddingValues = PaddingValues(),
    modifier: Modifier = Modifier,
    cardColor: Color = MaterialTheme.colorScheme.inversePrimary
) {
    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Adaptive(minSize = 280.dp),
        modifier = modifier,
        state = lazyStaggeredGridState,
        contentPadding = contentPadding + PaddingValues(8.dp),
        verticalItemSpacing = 8.dp,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(
            count = movies.itemCount,
            key = movies.itemKey { movie -> movie.movieId },
            contentType = movies.itemContentType()
        ) { index ->
            val movie = movies[index]
            if (movie != null) {
                val imageUrl = when (feedView) {
                    is FeedView.FeedList -> movie.backdropPath.formatBackdropImage
                    is FeedView.FeedGrid -> movie.posterPath.formatPosterImage
                }
                MovieCard(
                    imageUrl = imageUrl,
                    title = movie.title,
                    feedView = feedView,
                    onClick = { onMovieClick(movie.movieList, movie.movieId) },
                    cardColor = cardColor
                )
            }
        }
        when {
            movies.isPagingLoading -> {
                item(
                    span = StaggeredGridItemSpan.FullLine
                ) {
                    PagingLoadingBox()
                }
            }
            movies.isPagingFailure -> {
                item(
                    span = StaggeredGridItemSpan.FullLine
                ) {
                    Text(
                        text = "Failed to load movies. Tap to retry",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(80.dp)
                            .clip(MaterialTheme.shapes.large)
                            .clickable(onClick = movies::retry)
                            .wrapContentHeight(align = Alignment.CenterVertically),
                        style = MaterialTheme.typography.titleMedium.copy(
                            color = MaterialTheme.colorScheme.onErrorContainer,
                            textAlign = TextAlign.Center
                        )
                    )
                }
            }
        }
    }
}
