@file:OptIn(ExperimentalResourceApi::class)

package org.michaelbel.movies.details.ui

import android.content.Intent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.stringResource
import org.koin.androidx.compose.koinViewModel
import org.michaelbel.movies.common.theme.AppTheme
import org.michaelbel.movies.details.DetailsViewModel
import org.michaelbel.movies.ui.strings.MoviesStrings

@Composable
fun DetailsRoute(
    onBackClick: () -> Unit,
    onNavigateToGallery: (Int) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: DetailsViewModel = koinViewModel()
) {
    val detailsState by viewModel.detailsState.collectAsStateWithLifecycle()
    val networkStatus by viewModel.networkStatus.collectAsStateWithLifecycle()
    val currentTheme by viewModel.currentTheme.collectAsStateWithLifecycle()

    val resultContract = rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) {}
    val shareTitle = stringResource(MoviesStrings.share_via)

    val onShareUrl: (String) -> Unit = { url ->
        Intent().apply {
            type = "text/plain"
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, url)
        }.also { intent: Intent ->
            resultContract.launch(Intent.createChooser(intent, shareTitle))
        }
    }

    DetailsScreenContent(
        onBackClick = onBackClick,
        onShareClick = onShareUrl,
        onNavigateToGallery = onNavigateToGallery,
        onGenerateColors = viewModel::onGenerateColors,
        detailsState = detailsState,
        networkStatus = networkStatus,
        isThemeAmoled = currentTheme is AppTheme.Amoled,
        onRetry = viewModel::retry,
        modifier = modifier
    )
}