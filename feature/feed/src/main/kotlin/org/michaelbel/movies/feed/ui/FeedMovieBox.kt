package org.michaelbel.movies.feed.ui

import android.content.Context
import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import org.michaelbel.movies.entities.MovieData
import org.michaelbel.movies.ui.theme.MoviesTheme

@Composable
internal fun FeedMovieBox(
    modifier: Modifier = Modifier,
    movie: MovieData
) {
    val context: Context = LocalContext.current

    Column(
        modifier = modifier
            .clip(RoundedCornerShape(12F))
    ) {
        AsyncImage(
            model = ImageRequest.Builder(context)
                .data(movie.backdropPath)
                .crossfade(true)
                .build(),
            contentDescription = null,
            modifier = Modifier
                .height(220.dp)
                .fillMaxSize()
                .clip(RoundedCornerShape(12F)),
            contentScale = ContentScale.Crop
        )

        Text(
            text = movie.title,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            color = MaterialTheme.colorScheme.onBackground,
            maxLines = 10,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun MovieBoxPreview() {
    MoviesTheme {
        FeedMovieBox(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background),
            movie = MovieData(
                id = 438148,
                overview = """Миллион лет миньоны искали самого великого и ужасного предводителя, пока 
                не встретили ЕГО. Знакомьтесь - Грю. Пусть он еще очень молод, но у него в планах 
                по-настоящему гадкие дела, которые заставят планету содрогнуться.""",
                posterPath = "/19GXuePqcZSPD5JgT9MeVdeu9Tc.jpg",
                backdropPath = "https://image.tmdb.org/t/p/w500//nmGWzTLMXy9x7mKd8NKPLmHtWGa.jpg",
                releaseDate = "2022-06-29",
                title = "Миньоны: Грювитация",
                voteAverage = 7.6F,
                genreIds = listOf(16, 12, 35, 14)
            )
        )
    }
}