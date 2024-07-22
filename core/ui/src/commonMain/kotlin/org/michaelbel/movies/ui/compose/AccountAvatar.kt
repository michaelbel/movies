package org.michaelbel.movies.ui.compose

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.compose.LocalPlatformContext
import coil3.request.ImageRequest
import coil3.request.crossfade
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.michaelbel.movies.persistence.database.entity.pojo.AccountPojo
import org.michaelbel.movies.persistence.database.ktx.letters
import org.michaelbel.movies.ui.accessibility.MoviesContentDescriptionCommon
import org.michaelbel.movies.ui.ktx.lettersTextFontSizeSmall
import org.michaelbel.movies.ui.theme.MoviesTheme

@Composable
fun AccountAvatar(
    account: AccountPojo,
    fontSize: TextUnit,
    modifier: Modifier
) {
    if (account.avatarUrl.isNotEmpty()) {
        AsyncImage(
            model = ImageRequest.Builder(LocalPlatformContext.current)
                .data(account.avatarUrl)
                .crossfade(true)
                .build(),
            contentDescription = stringResource(MoviesContentDescriptionCommon.AccountAvatarImage),
            modifier = modifier.clip(CircleShape),
            contentScale = ContentScale.Crop
        )
    } else {
        Box(
            modifier = modifier.border(
                width = 2.dp,
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                shape = CircleShape
            ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = account.letters.uppercase(),
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                fontSize = fontSize
            )
        }
    }
}

@Preview
@Composable
private fun AccountAvatarPreview(
    /*@PreviewParameter(AccountPreviewParameterProvider::class)*/ account: AccountPojo
) {
    MoviesTheme {
        AccountAvatar(
            account = account,
            fontSize = account.lettersTextFontSizeSmall,
            modifier = Modifier.size(32.dp),
        )
    }
}