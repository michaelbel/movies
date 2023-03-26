package org.michaelbel.movies.auth.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.michaelbel.movies.auth.ktx.text
import org.michaelbel.movies.auth.ui.preview.ThrowablePreviewParameterProvider
import org.michaelbel.movies.ui.icons.MoviesIcons
import org.michaelbel.movies.ui.preview.DevicePreviews
import org.michaelbel.movies.ui.theme.MoviesTheme

@Composable
fun AuthErrorBox(
    modifier: Modifier = Modifier,
    error: Throwable?
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = MoviesIcons.Error,
            contentDescription = null,
            modifier = Modifier
                .size(24.dp),
            tint = MaterialTheme.colorScheme.error
        )

        Text(
            text = error.text,
            modifier = Modifier
                .padding(start = 4.dp),
            color = MaterialTheme.colorScheme.error,
            fontSize = 13.sp,
            textAlign = TextAlign.Start,
            lineHeight = 13.sp
        )
    }
}

@Composable
@DevicePreviews
private fun AuthErrorBoxPreview(
    @PreviewParameter(ThrowablePreviewParameterProvider::class) error: Throwable
) {
    MoviesTheme {
        AuthErrorBox(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.primaryContainer),
            error = error
        )
    }
}