@file:OptIn(ExperimentalMaterial3Api::class)

package org.michaelbel.movies.gallery

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerDefaults
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.LinearWavyProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.PlainTooltip
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.material3.TooltipAnchorPosition
import androidx.compose.material3.TooltipBox
import androidx.compose.material3.TooltipDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.WavyProgressIndicatorDefaults
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.material3.rememberTooltipState
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewWrapper
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import coil3.compose.AsyncImage
import coil3.compose.AsyncImagePainter
import coil3.compose.LocalPlatformContext
import coil3.request.ImageRequest
import coil3.request.crossfade
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf
import org.michaelbel.movies.gallery.event.GalleryEvent
import org.michaelbel.movies.gallery.intent.GalleryIntent
import org.michaelbel.movies.gallery.ktx.INFINITE_PAGER_PAGE_COUNT
import org.michaelbel.movies.gallery.ktx.calculateInitialPagerPage
import org.michaelbel.movies.gallery.ktx.pageToImageIndex
import org.michaelbel.movies.gallery.model.GalleryModel
import org.michaelbel.movies.gallery.zoomable.rememberZoomState
import org.michaelbel.movies.gallery.zoomable.zoomable
import org.michaelbel.movies.network.config.isNotOriginal
import org.michaelbel.movies.persistence.database.ktx.original
import org.michaelbel.movies.ui.ObserveAsEvents
import org.michaelbel.movies.ui.accessibility.MoviesContentDescription
import org.michaelbel.movies.ui.collectAsStateCommon
import org.michaelbel.movies.ui.compose.PlatformBackHandler
import org.michaelbel.movies.ui.displayCutoutWindowInsets
import org.michaelbel.movies.ui.icons.MoviesIcons
import org.michaelbel.movies.ui.modifierDisplayCutoutWindowInsets
import org.michaelbel.movies.ui.navigateToImageUri
import org.michaelbel.movies.ui.navigation.GalleryDestination
import org.michaelbel.movies.ui.preview.wrapper.ThemeWrapper
import org.michaelbel.movies.ui.strings.MoviesStrings
import org.michaelbel.movies.work.WorkInfoState

@Composable
fun GalleryScreen(
    destination: GalleryDestination,
    viewModel: GalleryViewModel = koinViewModel { parametersOf(destination) }
) {
    val state by viewModel.stateFlow.collectAsStateCommon()
    val openImage = navigateToImageUri()
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    val gallerySuccessText = stringResource(MoviesStrings.gallery_success)
    val galleryActionOpenText = stringResource(MoviesStrings.gallery_action_open)
    val galleryFailureText = stringResource(MoviesStrings.gallery_failure)

    GalleryScreenContent(
        state = state,
        dispatch = viewModel::dispatch,
        snackbarHostState = snackbarHostState
    )

    ObserveAsEvents(
        flow = viewModel.eventFlow,
        key1 = snackbarHostState
    ) { event ->
        when (event) {
            is GalleryEvent.DownloadResult -> {
                when (val workInfoState = event.workInfoState) {
                    is WorkInfoState.Success -> {
                        scope.launch {
                            val result = snackbarHostState.showSnackbar(
                                message = gallerySuccessText,
                                actionLabel = galleryActionOpenText,
                                duration = SnackbarDuration.Long
                            )
                            if (result == SnackbarResult.ActionPerformed) {
                                openImage(workInfoState.result)
                            }
                        }
                    }
                    is WorkInfoState.Failure -> {
                        if (workInfoState.result == "FAILURE_RESULT") {
                            scope.launch {
                                snackbarHostState.showSnackbar(
                                    message = galleryFailureText,
                                    duration = SnackbarDuration.Short
                                )
                            }
                        }
                    }
                    else -> Unit
                }
            }
        }
    }
}

@Composable
private fun GalleryScreenContent(
    state: GalleryModel,
    dispatch: (GalleryIntent) -> Unit,
    snackbarHostState: SnackbarHostState
) {
    val hapticFeedback = LocalHapticFeedback.current
    val platformContext = LocalPlatformContext.current
    val scope = rememberCoroutineScope()
    val imageCount = state.imagePojos.size
    val pagerPageCount = if (imageCount > 1) INFINITE_PAGER_PAGE_COUNT else imageCount
    val pagerState = rememberPagerState(pageCount = { pagerPageCount })
    var currentPage by remember { mutableIntStateOf(0) }
    var topBarHeightPx by remember { mutableIntStateOf(0) }
    val density = LocalDensity.current
    val topBarHeight = with(density) { topBarHeightPx.toDp() }
    val navigationBarBottomPadding = WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding()

    LaunchedEffect(pagerState, imageCount) {
        if (imageCount == 0) {
            currentPage = 0
            return@LaunchedEffect
        }

        if (imageCount > 1 && pagerState.currentPage == 0) {
            pagerState.scrollToPage(calculateInitialPagerPage(imageCount))
        }

        currentPage = pageToImageIndex(pagerState.currentPage, imageCount)

        snapshotFlow { pagerState.currentPage }.collect { page ->
            val nextPage = pageToImageIndex(page, imageCount)
            if (nextPage != currentPage) {
                hapticFeedback.performHapticFeedback(HapticFeedbackType.LongPress)
            }
            currentPage = nextPage
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        snackbarHost = {
            SnackbarHost(
                hostState = snackbarHostState,
                modifier = Modifier.navigationBarsPadding()
            )
        },
        containerColor = MaterialTheme.colorScheme.primaryContainer,
        contentWindowInsets = WindowInsets(0, 0, 0, 0)
    ) { _ ->
        when {
            state.imagePojos.isEmpty() -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    LinearWavyProgressIndicator(
                        trackColor = MaterialTheme.colorScheme.inversePrimary,
                        wavelength = WavyProgressIndicatorDefaults.LinearDeterminateWavelength,
                        waveSpeed = WavyProgressIndicatorDefaults.LinearDeterminateWavelength
                    )
                }
            }
            else -> {
                Box(
                    modifier = Modifier.fillMaxSize()
                ) {
                    HorizontalPager(
                        state = pagerState,
                        modifier = Modifier
                            .fillMaxSize()
                            .align(Alignment.Center)
                            .then(modifierDisplayCutoutWindowInsets),
                        pageSpacing = 8.dp,
                        flingBehavior = PagerDefaults.flingBehavior(state = pagerState)
                    ) { page ->
                        val imageDb = state.imagePojos[pageToImageIndex(page, imageCount)]
                        var imageDiskCacheKey: String? by remember { mutableStateOf(null) }
                        var image by remember { mutableStateOf("") }
                        image = imageDb.original
                        var loading by remember { mutableStateOf(true) }
                        var isZoomEnabled by remember { mutableStateOf(false) }
                        val zoomState = rememberZoomState()

                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .then(if (isZoomEnabled) Modifier.zoomable(zoomState) else Modifier),
                            contentAlignment = Alignment.Center
                        ) {
                            AsyncImage(
                                model = ImageRequest.Builder(platformContext)
                                    .data(image)
                                    .crossfade(true)
                                    .placeholderMemoryCacheKey(imageDiskCacheKey)
                                    .build(),
                                contentDescription = MoviesContentDescription.None,
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(top = topBarHeight, bottom = navigationBarBottomPadding),
                                transform = { state ->
                                    loading = state is AsyncImagePainter.State.Loading
                                    isZoomEnabled = state is AsyncImagePainter.State.Success
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
                                LinearWavyProgressIndicator(
                                    trackColor = MaterialTheme.colorScheme.inversePrimary,
                                    wavelength = WavyProgressIndicatorDefaults.LinearDeterminateWavelength,
                                    waveSpeed = WavyProgressIndicatorDefaults.LinearDeterminateWavelength
                                )
                            }

                            PlatformBackHandler(zoomState.isScaled) {
                                scope.launch { zoomState.reset() }
                            }
                        }
                    }

                    TopAppBar(
                        title = {
                            Text(
                                text = stringResource(MoviesStrings.gallery_image_of, currentPage.plus(1), state.imagePojos.size),
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                style = MaterialTheme.typography.titleLarge.copy(
                                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                                    textAlign = TextAlign.Start
                                )
                            )
                        },
                        modifier = Modifier
                            .align(Alignment.TopStart)
                            .onSizeChanged { topBarHeightPx = it.height }
                            .statusBarsPadding()
                            .windowInsetsPadding(displayCutoutWindowInsets)
                            .zIndex(1F),
                        navigationIcon = {
                            IconButton(
                                onClick = { dispatch(GalleryIntent.BackClick) }
                            ) {
                                Image(
                                    imageVector = MoviesIcons.ArrowBack,
                                    contentDescription = stringResource(MoviesContentDescription.BackIcon),
                                    modifier = Modifier.size(IconButtonDefaults.smallIconSize),
                                    colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onPrimaryContainer)
                                )
                            }
                        },
                        actions = {
                            TooltipBox(
                                positionProvider = TooltipDefaults.rememberTooltipPositionProvider(
                                    positioning = TooltipAnchorPosition.Below
                                ),
                                tooltip = {
                                    PlainTooltip {
                                        Text(
                                            text = stringResource(MoviesStrings.download)
                                        )
                                    }
                                },
                                state = rememberTooltipState()
                            ) {
                                IconButton(
                                    onClick = { dispatch(GalleryIntent.DownloadClick(state.imagePojos[currentPage])) },
                                    modifier = Modifier
                                        .minimumInteractiveComponentSize()
                                        .size(IconButtonDefaults.smallContainerSize(IconButtonDefaults.IconButtonWidthOption.Uniform)),
                                    shape = IconButtonDefaults.extraSmallSquareShape
                                ) {
                                    Image(
                                        imageVector = MoviesIcons.FileDownload,
                                        contentDescription = stringResource(MoviesContentDescription.DownloadIcon),
                                        modifier = Modifier.size(IconButtonDefaults.smallIconSize),
                                        colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onPrimaryContainer)
                                    )
                                }
                            }
                        },
                        colors = TopAppBarDefaults.topAppBarColors(
                            containerColor = Color.Transparent,
                            scrolledContainerColor = Color.Transparent
                        )
                    )
                }
            }
        }
    }
}

@PreviewWrapper(ThemeWrapper::class)
@Preview
@Composable
private fun GalleryScreenContentPreview() {
    GalleryScreenContent(
        state = GalleryModel(),
        dispatch = {},
        snackbarHostState = SnackbarHostState()
    )
}
