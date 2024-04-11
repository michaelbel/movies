package org.michaelbel.movies.gallery.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.androidx.compose.koinViewModel
import org.michaelbel.movies.gallery.GalleryViewModel

@Composable
fun GalleryRoute(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: GalleryViewModel = koinViewModel()
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