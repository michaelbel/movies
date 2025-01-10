package org.michaelbel.movies.gallery.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import org.michaelbel.movies.gallery.GalleryViewModel
import org.michaelbel.movies.ui.ktx.collectAsStateCommon

@Composable
actual fun GalleryRoute(
    onBackClick: () -> Unit,
    modifier: Modifier,
    viewModel: GalleryViewModel
) {
    val movieImages by viewModel.movieImagesFlow.collectAsStateCommon()
    val workInfoState by viewModel.workInfoStateFlow.collectAsStateCommon()

    GalleryScreenContent(
        movieImages = movieImages,
        workInfoState = workInfoState,
        onBackClick = onBackClick,
        onDownloadClick = viewModel::downloadImage,
        modifier = modifier
    )
}