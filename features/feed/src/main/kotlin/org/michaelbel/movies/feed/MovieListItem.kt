package org.michaelbel.movies.feed

import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import org.michaelbel.movies.core.image
import org.michaelbel.movies.core.model.MovieResponse

@Composable
fun MovieListItem(
    movie: MovieResponse,
    onClick: (MovieResponse) -> Unit,
    modifier: Modifier = Modifier
) {
    val context: Context = LocalContext.current

    Column(
        modifier = modifier
            .clip(RoundedCornerShape(12F))
            .clickable { onClick(movie) }
    ) {
        AsyncImage(
            model = ImageRequest.Builder(context)
                .data(image(movie.backdropPathSafe))
                .crossfade(true)
                .build(),
            contentDescription = null,
            modifier = Modifier
                .height(220.dp)
                .fillMaxSize()
                .clip(RoundedCornerShape(12f)),
            contentScale = ContentScale.Crop
        )

        Text(
            text = movie.title,
            maxLines = 10,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = 8.dp,
                    top = 8.dp,
                    end = 8.dp,
                    bottom = 8.dp
                )
        )
    }
}