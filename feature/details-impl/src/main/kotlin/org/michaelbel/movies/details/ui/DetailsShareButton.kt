package org.michaelbel.movies.details.ui

import android.content.Context
import android.content.Intent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.PreviewParameter
import org.michaelbel.movies.details_impl.R
import org.michaelbel.movies.persistence.database.entity.MovieDb
import org.michaelbel.movies.persistence.database.ktx.url
import org.michaelbel.movies.ui.icons.MoviesIcons
import org.michaelbel.movies.ui.preview.DevicePreviews
import org.michaelbel.movies.ui.preview.provider.MovieDbPreviewParameterProvider
import org.michaelbel.movies.ui.theme.MoviesTheme

@Composable
fun DetailsShareButton(
    movieUrl: String,
    modifier: Modifier = Modifier
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
                resultContract.launch(Intent.createChooser(intent, context.getString(R.string.details_share_via)))
            }
        },
        modifier = modifier
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
    @PreviewParameter(MovieDbPreviewParameterProvider::class) movie: MovieDb
) {
    MoviesTheme {
        DetailsShareButton(
            movieUrl = movie.url
        )
    }
}