package org.michaelbel.moviemade.ui.home

import androidx.compose.foundation.Image
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import org.michaelbel.moviemade.app.data.model.MovieResponse
import org.michaelbel.moviemade.app.image

@Composable
fun MovieListItem(
    movie: MovieResponse,
    onClick: (MovieResponse) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(12F))
            .clickable { onClick(movie) }
    ) {
        val imagePainter = rememberImagePainter(
            data = image(movie.backdropPathSafe)
        )
        Image(
            painter = imagePainter,
            contentDescription = null,
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .height(220.dp)
                .fillMaxSize()
                .clip(RoundedCornerShape(12f))
        )
        Text(
            text = movie.title,
            color = Color.Black,
            maxLines = 10,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 8.dp, top = 8.dp, end = 8.dp, bottom = 8.dp)
        )
    }
}