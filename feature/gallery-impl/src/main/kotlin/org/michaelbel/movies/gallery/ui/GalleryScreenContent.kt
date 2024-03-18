@file:OptIn(ExperimentalFoundationApi::class)

package org.michaelbel.movies.gallery.ui

import android.content.Intent
import android.net.Uri
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerDefaults
import androidx.compose.foundation.pager.PagerScope
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.core.net.toUri
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.work.WorkInfo
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import coil.request.ImageRequest
import kotlinx.coroutines.launch
import org.michaelbel.movies.common.theme.AppTheme
import org.michaelbel.movies.gallery.GalleryViewModel
import org.michaelbel.movies.gallery.zoomable.rememberZoomState
import org.michaelbel.movies.gallery.zoomable.zoomable
import org.michaelbel.movies.gallery_impl.R
import org.michaelbel.movies.network.isNotOriginal
import org.michaelbel.movies.persistence.database.entity.ImageDb
import org.michaelbel.movies.persistence.database.ktx.original
import org.michaelbel.movies.ui.accessibility.MoviesContentDescription
import org.michaelbel.movies.ui.compose.iconbutton.BackIcon
import org.michaelbel.movies.ui.compose.iconbutton.DownloadIcon
import org.michaelbel.movies.ui.ktx.displayCutoutWindowInsets
import org.michaelbel.movies.ui.preview.DevicePreviews
import org.michaelbel.movies.ui.theme.MoviesTheme
import org.michaelbel.movies.work.DownloadImageWorker

@Composable
fun GalleryRoute(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: GalleryViewModel = hiltViewModel()
) {
    val movieImages by viewModel.movieImagesFlow.collectAsStateWithLifecycle()
    val workInfo by viewModel.workInfoFlow.collectAsStateWithLifecycle()

    GalleryScreenContent(
        movieImages = movieImages,
        workInfo = workInfo,
        onBackClick = onBackClick,
        onDownloadClick = viewModel::downloadImage,
        modifier = modifier
    )
}

@Composable
private fun GalleryScreenContent(
    movieImages: List<ImageDb>,
    workInfo: WorkInfo?,
    onBackClick: () -> Unit,
    onDownloadClick: (ImageDb) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val hapticFeedback = LocalHapticFeedback.current
    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    val resultContract = rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) {}

    val pagerState = rememberPagerState(
        initialPage = 0,
        initialPageOffsetFraction = 0F,
        pageCount = { movieImages.size }
    )
    var currentPage by remember { mutableIntStateOf(0) }
    LaunchedEffect(pagerState) {
        snapshotFlow { pagerState.currentPage }.collect { page ->
            hapticFeedback.performHapticFeedback(HapticFeedbackType.LongPress)
            currentPage = page
        }
    }

    val onSuccessSnackbar: (String, String, Uri) -> Unit = { message, actionLabel, uri ->
        coroutineScope.launch {
            val result = snackbarHostState.showSnackbar(
                message = message,
                actionLabel = actionLabel,
                duration = SnackbarDuration.Long
            )
            if (result == SnackbarResult.ActionPerformed) {
                Intent(Intent.ACTION_VIEW).apply {
                    setDataAndType(uri, "image/jpg")
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                }.also { intent ->
                    resultContract.launch(intent)
                }
            }
        }
    }
    val onFailureSnackbar: (String) -> Unit = { message ->
        coroutineScope.launch {
            snackbarHostState.showSnackbar(
                message = message,
                duration = SnackbarDuration.Short
            )
        }
    }

    when (workInfo?.state) {
        WorkInfo.State.SUCCEEDED -> {
            val result = workInfo.outputData.getString(DownloadImageWorker.KEY_IMAGE_URL).orEmpty()
            onSuccessSnackbar(
                stringResource(R.string.gallery_success),
                stringResource(R.string.gallery_action_open),
                result.toUri()
            )
        }
        WorkInfo.State.FAILED -> {
            val result = workInfo.outputData.getString(DownloadImageWorker.KEY_IMAGE_URL).orEmpty()
            if (result == DownloadImageWorker.FAILURE_RESULT) {
                onFailureSnackbar(stringResource(R.string.gallery_failure))
            }
        }
        else -> {}
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },
        containerColor = MaterialTheme.colorScheme.primaryContainer
    ) { innerPadding ->
        when {
            movieImages.isEmpty() -> {
                GalleryLoading(
                    modifier = Modifier.fillMaxSize()
                )
            }
            else -> {
                ConstraintLayout(
                    modifier = Modifier.fillMaxSize()
                ) {
                    val (pager, backIcon, title, downloadIcon) = createRefs()

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
                        val imageDb = movieImages[page]
                        var imageDiskCacheKey: String? by remember { mutableStateOf(null) }

                        var image by remember { mutableStateOf("") }
                        image = imageDb.original

                        var loading: Boolean by remember { mutableStateOf(true) }

                        ConstraintLayout(
                            modifier = Modifier.fillMaxSize()
                        ) {
                            val (asyncImage, progressBar) = createRefs()

                            val zoomState = rememberZoomState()

                            AsyncImage(
                                model = ImageRequest.Builder(context)
                                    .data(image)
                                    .crossfade(true)
                                    .placeholderMemoryCacheKey(imageDiskCacheKey)
                                    .build(),
                                contentDescription = MoviesContentDescription.None,
                                modifier = Modifier
                                    .constrainAs(asyncImage) {
                                        width = Dimension.fillToConstraints
                                        height = Dimension.fillToConstraints
                                        start.linkTo(parent.start)
                                        top.linkTo(parent.top)
                                        end.linkTo(parent.end)
                                        bottom.linkTo(parent.bottom)
                                    }
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
                                    modifier = Modifier
                                        .constrainAs(progressBar) {
                                            width = Dimension.wrapContent
                                            height = Dimension.wrapContent
                                            start.linkTo(parent.start)
                                            top.linkTo(parent.top)
                                            end.linkTo(parent.end)
                                            bottom.linkTo(parent.bottom)
                                        }
                                        .zoomable(zoomState),
                                    trackColor = MaterialTheme.colorScheme.inversePrimary
                                )
                            }

                            BackHandler(zoomState.isScaled) {
                                coroutineScope.launch { zoomState.reset() }
                            }
                        }
                    }

                    BackIcon(
                        onClick = onBackClick,
                        modifier = Modifier
                            .constrainAs(backIcon) {
                                width = Dimension.wrapContent
                                height = Dimension.wrapContent
                                start.linkTo(parent.start, 4.dp)
                                top.linkTo(parent.top, 8.dp)
                            }
                            .statusBarsPadding()
                            .windowInsetsPadding(displayCutoutWindowInsets)
                    )

                    Text(
                        text = stringResource(R.string.gallery_image_of, currentPage.plus(1), movieImages.size),
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
                        textAlign = TextAlign.Start,
                        style = MaterialTheme.typography.titleLarge.copy(MaterialTheme.colorScheme.onPrimaryContainer)
                    )

                    AnimatedVisibility(
                        visible = true,
                        modifier = Modifier
                            .constrainAs(downloadIcon) {
                                width = Dimension.wrapContent
                                height = Dimension.wrapContent
                                end.linkTo(parent.end, 4.dp)
                                top.linkTo(parent.top, 8.dp)
                            }
                            .statusBarsPadding(),
                        enter = fadeIn()
                    ) {
                        DownloadIcon(
                            onClick = { onDownloadClick(movieImages[currentPage]) },
                            modifier = Modifier.windowInsetsPadding(displayCutoutWindowInsets)
                        )
                    }
                }
            }
        }

        innerPadding.toString()
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

@Composable
@DevicePreviews
private fun GalleryScreenContentPreview() {
    MoviesTheme {
        GalleryScreenContent(
            movieImages = emptyList(),
            workInfo = null,
            onBackClick = {},
            onDownloadClick = {},
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Composable
@Preview
private fun GalleryScreenContentAmoledPreview() {
    MoviesTheme(
        theme = AppTheme.Amoled
    ) {
        GalleryScreenContent(
            movieImages = emptyList(),
            workInfo = null,
            onBackClick = {},
            onDownloadClick = {},
            modifier = Modifier.fillMaxSize()
        )
    }
}