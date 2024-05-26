package org.michaelbel.movies.gallery.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import org.koin.androidx.compose.koinViewModel
import org.michaelbel.movies.gallery.GalleryViewModel
import org.michaelbel.movies.ui.ktx.collectAsStateCommon

@Composable
fun GalleryRoute(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: GalleryViewModel = koinViewModel()
) {
    val movieImages by viewModel.movieImagesFlow.collectAsStateCommon()
    val workInfo by viewModel.workInfoFlow.collectAsStateCommon()

    GalleryScreenContent(
        movieImages = movieImages,
        workInfo = workInfo,
        onBackClick = onBackClick,
        onDownloadClick = viewModel::downloadImage,
        modifier = modifier
    )
}