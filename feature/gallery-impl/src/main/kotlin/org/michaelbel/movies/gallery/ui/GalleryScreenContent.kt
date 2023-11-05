package org.michaelbel.movies.gallery.ui

import android.content.Context
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerScope
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import coil.request.ImageRequest
import kotlinx.coroutines.launch
import org.michaelbel.movies.entities.image.original
import org.michaelbel.movies.gallery.GalleryViewModel
import org.michaelbel.movies.gallery.zoomable.rememberZoomState
import org.michaelbel.movies.gallery.zoomable.zoomable
import org.michaelbel.movies.ui.icons.MoviesIcons

@Composable
fun GalleryRoute(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: GalleryViewModel = hiltViewModel()
) {
    val movieImage: String by viewModel.imageFlow.collectAsStateWithLifecycle()

    GalleryScreenContent(
        movieImage = movieImage,
        onBackClick = onBackClick,
        modifier = modifier
    )
}

@Composable
private fun GalleryScreenContent(
    movieImage: String,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context: Context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    var imageDiskCacheKey: String? by remember { mutableStateOf(null) }
    var image: String by remember { mutableStateOf("") }
    image = movieImage

    ConstraintLayout(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primaryContainer)
    ) {
        val (pager, backIcon) = createRefs()

        LoopHorizontalPager(
            count = 1,
            modifier = Modifier.constrainAs(pager) {
                width = Dimension.fillToConstraints
                height = Dimension.wrapContent
                start.linkTo(parent.start)
                top.linkTo(parent.top)
                end.linkTo(parent.end)
                bottom.linkTo(parent.bottom)
            }
        ) {
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
                        if (state is AsyncImagePainter.State.Success) {
                            zoomState.setContentSize(state.painter.intrinsicSize)
                            imageDiskCacheKey = state.result.diskCacheKey
                            if (image != image.original) {
                                image = image.original
                            }
                        }
                        state
                    },
                    contentScale = ContentScale.Fit
                )

                BackHandler(zoomState.isScaled) {
                    coroutineScope.launch { zoomState.reset() }
                }
            }
        }

        IconButton(
            onClick = onBackClick,
            modifier = Modifier.constrainAs(backIcon) {
                width = Dimension.wrapContent
                height = Dimension.wrapContent
                start.linkTo(parent.start, 4.dp)
                top.linkTo(parent.top, 8.dp)
            }.statusBarsPadding()
        ) {
            Image(
                imageVector = MoviesIcons.ArrowBack,
                contentDescription = null,
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onPrimaryContainer)
            )
        }
    }
}

@Composable
private fun LoopHorizontalPager(
    count: Int,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    content: @Composable PagerScope.(page: Int) -> Unit,
) {
    val startIndex: Int = Int.MAX_VALUE / 2
    val pagerState = rememberPagerState(
        initialPage = startIndex,
        initialPageOffsetFraction = 0F,
        pageCount = { count }
    )
    HorizontalPager(
        state = pagerState,
        modifier = modifier,
        contentPadding = contentPadding,
        pageContent = { index ->
            val page = (index - startIndex).floorMod(count)
            content(page)
        }
    )
}

private fun Int.floorMod(other: Int): Int = when (other) {
    0 -> this
    else -> this - floorDiv(other) * other
}