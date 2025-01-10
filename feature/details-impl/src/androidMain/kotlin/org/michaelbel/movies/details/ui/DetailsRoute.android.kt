package org.michaelbel.movies.details.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import org.jetbrains.compose.resources.stringResource
import org.michaelbel.movies.common.theme.AppTheme
import org.michaelbel.movies.details.DetailsViewModel
import org.michaelbel.movies.ui.ktx.collectAsStateCommon
import org.michaelbel.movies.ui.ktx.navigateToShareText
import org.michaelbel.movies.ui.strings.MoviesStrings

@Composable
actual fun DetailsRoute(
    onBackClick: () -> Unit,
    onNavigateToGallery: (Int) -> Unit,
    modifier: Modifier,
    viewModel: DetailsViewModel
) {
    val detailsState by viewModel.detailsState.collectAsStateCommon()
    val networkStatus by viewModel.networkStatus.collectAsStateCommon()
    val currentTheme by viewModel.currentTheme.collectAsStateCommon()

    val context = LocalContext.current
    val shareTitle = stringResource(MoviesStrings.share_via)

    DetailsScreenContent(
        onBackClick = onBackClick,
        onShareClick = { context.navigateToShareText(it, shareTitle) },
        onNavigateToGallery = onNavigateToGallery,
        onGenerateColors = viewModel::onGenerateColors,
        detailsState = detailsState,
        networkStatus = networkStatus,
        isThemeAmoled = currentTheme is AppTheme.Amoled,
        onRetry = viewModel::retry,
        modifier = modifier
    )
}