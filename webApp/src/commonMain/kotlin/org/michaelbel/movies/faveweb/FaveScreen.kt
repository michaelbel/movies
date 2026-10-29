@file:OptIn(ExperimentalMaterial3ExpressiveApi::class)

package org.michaelbel.movies.faveweb

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialShapes
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.toPath
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.center
import androidx.compose.ui.graphics.Matrix
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.graphics.shapes.Morph
import androidx.paging.compose.collectAsLazyPagingItems
import org.koin.compose.koinInject
import org.michaelbel.movies.common.appearance.FeedView
import org.michaelbel.movies.common.exceptions.PageEmptyException
import org.michaelbel.movies.domain.usecase.CurrentFeedViewFlowUseCase
import org.michaelbel.movies.domain.usecase.FavoriteMoviesPagingDataUseCase
import org.michaelbel.movies.feed.ktx.toUserMessage
import org.michaelbel.movies.interactor.LocaleInteractor
import org.michaelbel.movies.main.MainWebDestination
import org.michaelbel.movies.main.tabs.event.MainWebTabsEventManager
import org.michaelbel.movies.persistence.database.typealiases.MovieId
import org.michaelbel.movies.persistence.database.typealiases.PagingKey
import org.michaelbel.movies.ui.compose.page.PageContent
import org.michaelbel.movies.ui.compose.page.PageFailure
import org.michaelbel.movies.ui.compose.page.PageLoading
import org.michaelbel.movies.ui.icons.MoviesIcons
import org.michaelbel.movies.ui.isFailure
import org.michaelbel.movies.ui.isLoading
import org.michaelbel.movies.ui.refreshThrowable

@Composable
fun FaveScreen(
    onMovieClick: (PagingKey, MovieId) -> Unit = { _, _ -> },
    localeInteractor: LocaleInteractor = koinInject(),
    currentFeedViewFlowUseCase: CurrentFeedViewFlowUseCase = koinInject(),
    favoriteMoviesPagingDataUseCase: FavoriteMoviesPagingDataUseCase = koinInject()
) {
    val feedView by remember(currentFeedViewFlowUseCase) {
        currentFeedViewFlowUseCase(Unit)
    }.collectAsState(initial = FeedView.FeedList)
    val movies = remember(favoriteMoviesPagingDataUseCase, localeInteractor) {
        favoriteMoviesPagingDataUseCase(localeInteractor.language)
    }.collectAsLazyPagingItems()

    val lazyStaggeredGridState = rememberLazyStaggeredGridState()

    LaunchedEffect(Unit) {
        MainWebTabsEventManager.reselectFlow.collect { destination ->
            when (destination) {
                MainWebDestination.Fave -> {
                    lazyStaggeredGridState.animateScrollToItem(0)
                }
                else -> Unit
            }
        }
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.primaryContainer
    ) {
        when {
            movies.isLoading -> {
                PageLoading(
                    feedView = feedView,
                    modifier = Modifier.fillMaxSize()
                )
            }
            movies.isFailure -> {
                when {
                    movies.refreshThrowable is PageEmptyException -> {
                        val shapeSequence = remember {
                            listOf(
                                MaterialShapes.Cookie12Sided,
                                MaterialShapes.Cookie9Sided,
                                MaterialShapes.Cookie7Sided,
                                MaterialShapes.Cookie6Sided,
                                MaterialShapes.Cookie4Sided,
                                MaterialShapes.Cookie6Sided,
                                MaterialShapes.Cookie7Sided,
                                MaterialShapes.Cookie9Sided,
                                MaterialShapes.Cookie12Sided
                            )
                        }
                        val morphs = remember(shapeSequence) {
                            shapeSequence.zipWithNext { start, end ->
                                Morph(start = start, end = end)
                            }
                        }
                        val infiniteTransition = rememberInfiniteTransition(label = "rotating_cookie_shape")
                        val rotation by infiniteTransition.animateFloat(
                            initialValue = 0F,
                            targetValue = 360F,
                            animationSpec = infiniteRepeatable(
                                animation = tween(durationMillis = 8_000, easing = LinearEasing),
                                repeatMode = RepeatMode.Restart
                            ),
                            label = "rotating_cookie_shape_rotation"
                        )
                        val progress by infiniteTransition.animateFloat(
                            initialValue = 0F,
                            targetValue = morphs.size.toFloat(),
                            animationSpec = infiniteRepeatable(
                                animation = tween(durationMillis = 8_000, easing = LinearEasing),
                                repeatMode = RepeatMode.Restart
                            ),
                            label = "rotating_cookie_shape_morph"
                        )
                        val cycleProgress = when {
                            progress >= morphs.size -> 0F
                            else -> progress
                        }
                        val morphIndex = cycleProgress.toInt()
                        val morphFraction = cycleProgress - morphIndex
                        val path = remember { Path() }
                        val cookieColor = MaterialTheme.colorScheme.primary

                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(horizontal = 16.dp),
                            verticalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterVertically),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Box(
                                modifier = Modifier.size(164.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Canvas(
                                    modifier = Modifier.matchParentSize()
                                ) {
                                    path.reset()
                                    morphs[morphIndex].toPath(progress = morphFraction, path = path)
                                    path.transform(Matrix().apply { scale(x = size.width, y = size.height) })
                                    path.translate(size.center - path.getBounds().center)

                                    rotate(degrees = rotation, pivot = center) {
                                        drawPath(path = path, color = cookieColor)
                                    }
                                }

                                Icon(
                                    imageVector = MoviesIcons.LocalMovies,
                                    contentDescription = null,
                                    modifier = Modifier.size(72.dp),
                                    tint = MaterialTheme.colorScheme.onPrimary
                                )
                            }

                            Text(
                                text = "Movie List is Empty",
                                modifier = Modifier.fillMaxWidth(),
                                style = MaterialTheme.typography.headlineSmall.copy(
                                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                                    fontWeight = FontWeight.SemiBold,
                                    textAlign = TextAlign.Center
                                )
                            )
                        }
                    }
                    else -> {
                        PageFailure(
                            message = movies.refreshThrowable.toUserMessage(),
                            onRetryClick = movies::retry,
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                }
            }
            else -> {
                PageContent(
                    movies = movies,
                    feedView = feedView,
                    onMovieClick = onMovieClick,
                    lazyStaggeredGridState = lazyStaggeredGridState,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}
