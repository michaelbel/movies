package org.michaelbel.movies.gallery.ui

import android.content.Intent
import android.net.Uri
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.windowInsetsPadding
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
import androidx.compose.ui.Alignment
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
import androidx.core.net.toUri
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import coil.request.ImageRequest
import kotlinx.coroutines.launch
import org.michaelbel.movies.gallery.zoomable.rememberZoomState
import org.michaelbel.movies.gallery.zoomable.zoomable
import org.michaelbel.movies.gallery_impl.R
import org.michaelbel.movies.network.config.isNotOriginal
import org.michaelbel.movies.persistence.database.entity.pojo.ImagePojo
import org.michaelbel.movies.persistence.database.ktx.original
import org.michaelbel.movies.ui.accessibility.MoviesContentDescription
import org.michaelbel.movies.ui.compose.iconbutton.BackIcon
import org.michaelbel.movies.ui.compose.iconbutton.DownloadIcon
import org.michaelbel.movies.ui.ktx.displayCutoutWindowInsets
import org.michaelbel.movies.ui.theme.MoviesTheme
import org.michaelbel.movies.work.DownloadImageWorker
import org.michaelbel.movies.work.WorkInfoState

@Composable
internal fun GalleryScreenContent(
    movieImages: List<ImagePojo>,
    workInfoState: WorkInfoState,
    onBackClick: () -> Unit,
    onDownloadClick: (ImagePojo) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val hapticFeedback = LocalHapticFeedback.current
    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    val resultContract = rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) {}

    val pagerState = rememberPagerState(pageCount = { movieImages.size })
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

    when (workInfoState) {
        is WorkInfoState.Success -> {
            onSuccessSnackbar(
                stringResource(R.string.gallery_success),
                stringResource(R.string.gallery_action_open),
                workInfoState.result.toUri()
            )
        }
        is WorkInfoState.Failure -> {
            if (workInfoState.result == DownloadImageWorker.FAILURE_RESULT) {
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
                Box(
                    modifier = Modifier.fillMaxSize()
                ) {
                    LoopHorizontalPager(
                        pagerState = pagerState,
                        modifier = Modifier
                            .fillMaxSize()
                            .align(Alignment.Center)
                    ) { page ->
                        val imageDb = movieImages[page]
                        var imageDiskCacheKey: String? by remember { mutableStateOf(null) }

                        var image by remember { mutableStateOf("") }
                        image = imageDb.original

                        var loading: Boolean by remember { mutableStateOf(true) }

                        val zoomState = rememberZoomState()

                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            AsyncImage(
                                model = ImageRequest.Builder(context)
                                    .data(image)
                                    .crossfade(true)
                                    .placeholderMemoryCacheKey(imageDiskCacheKey)
                                    .build(),
                                contentDescription = MoviesContentDescription.None,
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
                                    modifier = Modifier.zoomable(zoomState),
                                    trackColor = MaterialTheme.colorScheme.inversePrimary
                                )
                            }

                            BackHandler(zoomState.isScaled) {
                                coroutineScope.launch { zoomState.reset() }
                            }
                        }
                    }

                    Row(
                        modifier = Modifier
                            .align(Alignment.TopStart)
                            .padding(start = 4.dp, top = 8.dp, end = 4.dp)
                            .statusBarsPadding()
                            .windowInsetsPadding(displayCutoutWindowInsets),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        BackIcon(
                            onClick = onBackClick
                        )

                        Text(
                            text = stringResource(R.string.gallery_image_of, currentPage.plus(1), movieImages.size),
                            overflow = TextOverflow.Ellipsis,
                            textAlign = TextAlign.Start,
                            style = MaterialTheme.typography.titleLarge.copy(MaterialTheme.colorScheme.onPrimaryContainer)
                        )
                    }

                    AnimatedVisibility(
                        visible = true,
                        enter = fadeIn(),
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(end = 4.dp, top = 8.dp)
                            .statusBarsPadding()
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

@Preview
@Composable
private fun GalleryScreenContentPreview() {
    MoviesTheme {
        GalleryScreenContent(
            movieImages = emptyList(),
            workInfoState = WorkInfoState.None,
            onBackClick = {},
            onDownloadClick = {},
            modifier = Modifier.fillMaxSize()
        )
    }
}