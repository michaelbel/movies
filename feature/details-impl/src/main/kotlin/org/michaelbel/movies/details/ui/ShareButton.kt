package org.michaelbel.movies.details.ui

import android.content.Context
import android.content.Intent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.PreviewParameter
import org.michaelbel.movies.details.ui.preview.MoviePreviewParameterProvider
import org.michaelbel.movies.details_impl.R
import org.michaelbel.movies.domain.data.entity.MovieDb
import org.michaelbel.movies.domain.data.ktx.url
import org.michaelbel.movies.ui.icon.MoviesIcons
import org.michaelbel.movies.ui.preview.DevicePreviews
import org.michaelbel.movies.ui.theme.MoviesTheme

@Composable
internal fun ShareButton(
    movieUrl: String
) {
    val context: Context = LocalContext.current
    val resultContract = rememberLauncherForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {}

    IconButton(
        onClick = {
            Intent().apply {
                type = "text/plain"
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, movieUrl)
            }.also { intent: Intent ->
                resultContract.launch(
                    Intent.createChooser(intent, context.getString(R.string.details_share_via))
                )
            }
        }
    ) {
        Image(
            imageVector = MoviesIcons.Share,
            contentDescription = null,
            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onPrimaryContainer)
        )
    }
}

@Composable
@DevicePreviews
private fun ShareButtonPreview(
    @PreviewParameter(MoviePreviewParameterProvider::class) movie: MovieDb
) {
    MoviesTheme {
        ShareButton(
            movieUrl = movie.url
        )
    }
}