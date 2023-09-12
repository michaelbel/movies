package org.michaelbel.movies.ui.compose

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import org.michaelbel.movies.persistence.database.entity.AccountDb
import org.michaelbel.movies.persistence.database.ktx.letters
import org.michaelbel.movies.ui.ktx.context
import org.michaelbel.movies.ui.ktx.lettersTextFontSizeSmall
import org.michaelbel.movies.ui.preview.DevicePreviews
import org.michaelbel.movies.ui.preview.provider.AccountPreviewParameterProvider
import org.michaelbel.movies.ui.theme.MoviesTheme

@Composable
fun AccountAvatar(
    account: AccountDb,
    fontSize: TextUnit,
    modifier: Modifier = Modifier
) {
    var isErrorLoadingAvatar: Boolean by remember { mutableStateOf(false) }

    if (account.avatarUrl.isNotEmpty() && !isErrorLoadingAvatar) {
        SubcomposeAsyncImage(
            model = ImageRequest.Builder(context)
                .data(account.avatarUrl)
                .crossfade(true)
                .build(),
            contentDescription = null,
            modifier = modifier.clip(CircleShape),
            contentScale = ContentScale.Crop
        ) {
            val state: AsyncImagePainter.State = painter.state
            isErrorLoadingAvatar = state is AsyncImagePainter.State.Error || state is AsyncImagePainter.State.Empty
        }
    } else {
        Box(
            modifier = modifier
                .border(
                    width = 2.dp,
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    shape = CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = account.letters.uppercase(),
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                fontSize = fontSize,
                maxLines = 1
            )
        }
    }
}

@Composable
@DevicePreviews
private fun AccountAvatarPreview(
    @PreviewParameter(AccountPreviewParameterProvider::class) account: AccountDb
) {
    MoviesTheme {
        AccountAvatar(
            account = account,
            fontSize = account.lettersTextFontSizeSmall,
            modifier = Modifier.size(32.dp),
        )
    }
}