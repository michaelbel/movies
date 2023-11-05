package org.michaelbel.movies.gallery.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerDefaults
import androidx.compose.foundation.pager.PagerScope
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import coil.request.ImageRequest
import kotlinx.coroutines.launch
import org.michaelbel.movies.network.isNotOriginal
import org.michaelbel.movies.gallery.GalleryViewModel
import org.michaelbel.movies.gallery.zoomable.rememberZoomState
import org.michaelbel.movies.gallery.zoomable.zoomable
import org.michaelbel.movies.persistence.database.entity.ImageDb
import org.michaelbel.movies.persistence.database.ktx.original
import org.michaelbel.movies.ui.icons.MoviesIcons

@Composable
fun GalleryRoute(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: GalleryViewModel = hiltViewModel()
) {
    val movieImages: List<ImageDb> by viewModel.movieImagesFlow.collectAsStateWithLifecycle()
    val backdropPosition: Int by viewModel.backdropPositionFlow.collectAsStateWithLifecycle()

    GalleryScreenContent(
        movieImages = movieImages,
        backdropPosition = backdropPosition,
        onBackClick = onBackClick,
        modifier = modifier
    )
}

@Composable
private fun GalleryScreenContent(
    movieImages: List<ImageDb>,
    backdropPosition: Int,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val hapticFeedback = LocalHapticFeedback.current
    val coroutineScope = rememberCoroutineScope()

    ConstraintLayout(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primaryContainer)
    ) {
        val (pager, backIcon, title) = createRefs()

        val initialPage = 0
        val pagerState = rememberPagerState(
            initialPage = initialPage,
            initialPageOffsetFraction = 0F,
            pageCount = { movieImages.size }
        )

        var currentPage: Int by remember { mutableStateOf(0) }
        LaunchedEffect(pagerState) {
            snapshotFlow { pagerState.currentPage }.collect { page ->
                if (currentPage != page) {
                    hapticFeedback.performHapticFeedback(hapticFeedbackType = HapticFeedbackType.LongPress)
                    currentPage = page
                }
            }
        }

        LoopHorizontalPager(
            pagerState = pagerState,
            modifier = Modifier.constrainAs(pager) {
                width = Dimension.fillToConstraints
                height = Dimension.wrapContent
                start.linkTo(parent.start)
                top.linkTo(parent.top)
                end.linkTo(parent.end)
                bottom.linkTo(parent.bottom)
            }
        ) { page ->
            currentPage = page

            val imageDb: ImageDb = movieImages[page]
            var imageDiskCacheKey: String? by remember { mutableStateOf(null) }

            var image: String by remember { mutableStateOf("") }
            image = imageDb.original

            var loading: Boolean by remember { mutableStateOf(true) }

            Box(
                contentAlignment = Alignment.Center
            ) {
                val zoomState = rememberZoomState()

                AsyncImage(
                    model = ImageRequest.Builder(context)
                        .data(image)
                        .crossfade(true)
                        .placeholderMemoryCacheKey(imageDiskCacheKey)
                        .build(),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxSize()
                        .zoomable(zoomState),
                    transform = { state ->
                        loading = state is AsyncImagePainter.State.Loading

                        if (state is AsyncImagePainter.State.Success) {
                            zoomState.setContentSize(state.painter.intrinsicSize)
                            imageDiskCacheKey = state.result.diskCacheKey
                            if (image.isNotOriginal) {
                                image = imageDb.original
                            }
                        }

                        state
                    },
                    contentScale = ContentScale.Fit
                )

                if (loading) {
                    LinearProgressIndicator(
                        trackColor = MaterialTheme.colorScheme.inversePrimary
                    )
                }

                BackHandler(zoomState.isScaled) {
                    coroutineScope.launch { zoomState.reset() }
                }
            }
        }

        IconButton(
            onClick = onBackClick,
            modifier = Modifier
                .constrainAs(backIcon) {
                    width = Dimension.wrapContent
                    height = Dimension.wrapContent
                    start.linkTo(parent.start, 4.dp)
                    top.linkTo(parent.top, 8.dp)
                }
                .statusBarsPadding()
        ) {
            Image(
                imageVector = MoviesIcons.ArrowBack,
                contentDescription = null,
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onPrimaryContainer)
            )
        }

        Text(
            text = "",
            modifier = Modifier
                .constrainAs(title) {
                    width = Dimension.wrapContent
                    height = Dimension.wrapContent
                    start.linkTo(backIcon.end, 4.dp)
                    top.linkTo(backIcon.top)
                    bottom.linkTo(backIcon.bottom)
                }
                .statusBarsPadding(),
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
            style = MaterialTheme.typography.titleLarge.copy(
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
        )
    }
}

@Composable
private fun LoopHorizontalPager(
    pagerState: PagerState,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    content: @Composable PagerScope.(page: Int) -> Unit,
) {
    HorizontalPager(
        state = pagerState,
        modifier = modifier,
        contentPadding = contentPadding,
        pageSpacing = 8.dp,
        flingBehavior = PagerDefaults.flingBehavior(state = pagerState),
        pageContent = { index ->
            content(index)
        }
    )
}