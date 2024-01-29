package org.michaelbel.movies.ui.compose.iconbutton

import android.content.Context
import android.content.Intent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import org.michaelbel.movies.persistence.database.entity.MovieDb
import org.michaelbel.movies.persistence.database.ktx.url
import org.michaelbel.movies.ui.R
import org.michaelbel.movies.ui.accessibility.MoviesContentDescription
import org.michaelbel.movies.ui.icons.MoviesIcons
import org.michaelbel.movies.ui.preview.DevicePreviews
import org.michaelbel.movies.ui.preview.provider.MovieDbPreviewParameterProvider
import org.michaelbel.movies.ui.theme.AmoledTheme
import org.michaelbel.movies.ui.theme.MoviesTheme

@Composable
fun ShareIcon(
    url: String,
    modifier: Modifier = Modifier
) {
    val context: Context = LocalContext.current
    val resultContract = rememberLauncherForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {}

    val onShareUrl: () -> Unit = {
        Intent().apply {
            type = "text/plain"
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, url)
        }.also { intent: Intent ->
            resultContract.launch(Intent.createChooser(intent, context.getString(R.string.share_via)))
        }
    }

    IconButton(
        onClick = onShareUrl,
        modifier = modifier
    ) {
        Image(
            imageVector = MoviesIcons.Share,
            contentDescription = stringResource(MoviesContentDescription.ShareIcon),
            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onPrimaryContainer)
        )
    }
}

@Composable
@DevicePreviews
private fun ShareIconPreview(
    @PreviewParameter(MovieDbPreviewParameterProvider::class) movie: MovieDb
) {
    MoviesTheme {
        ShareIcon(
            url = movie.url,
            modifier = Modifier.background(MaterialTheme.colorScheme.primaryContainer)
        )
    }
}

@Composable
@Preview
private fun ShareIconAmoledPreview(
    @PreviewParameter(MovieDbPreviewParameterProvider::class) movie: MovieDb
) {
    AmoledTheme {
        ShareIcon(
            url = movie.url,
            modifier = Modifier.background(MaterialTheme.colorScheme.primaryContainer)
        )
    }
}