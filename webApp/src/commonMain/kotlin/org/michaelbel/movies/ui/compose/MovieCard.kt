package org.michaelbel.movies.ui.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.compose.LocalPlatformContext
import coil3.request.ImageRequest
import coil3.request.crossfade
import org.michaelbel.movies.common.appearance.FeedView

@Composable
fun MovieCard(
    imageUrl: String,
    title: String,
    feedView: FeedView,
    maxLines: Int = 10,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    val cardColor = MaterialTheme.colorScheme.inversePrimary
    val imagePainter = ColorPainter(cardColor)
    val aspectRatio = when (feedView) {
        is FeedView.FeedList -> 16F / 9F
        is FeedView.FeedGrid -> 3F / 4F
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(MaterialTheme.shapes.large)
            .background(cardColor)
            .clickable(onClick = onClick)
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalPlatformContext.current)
                .data(imageUrl.ifBlank { null })
                .crossfade(true)
                .build(),
            contentDescription = null,
            placeholder = imagePainter,
            error = imagePainter,
            fallback = imagePainter,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(aspectRatio),
            contentScale = ContentScale.Crop
        )

        Text(
            text = title,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            maxLines = maxLines,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.bodyLarge.copy(
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
        )
    }
}
