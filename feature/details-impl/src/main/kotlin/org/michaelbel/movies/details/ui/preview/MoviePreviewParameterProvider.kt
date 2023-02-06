package org.michaelbel.movies.details.ui.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import org.michaelbel.movies.entities.MovieDetailsData

internal class MoviePreviewParameterProvider: PreviewParameterProvider<MovieDetailsData> {
    override val values: Sequence<MovieDetailsData> = sequenceOf(
        MovieDetailsData(
            id = 438148,
            overview = """Миллион лет миньоны искали самого великого и ужасного предводителя, 
                    пока не встретили ЕГО. Знакомьтесь - Грю. Пусть он еще очень молод, но у него 
                    в планах по-настоящему гадкие дела, которые заставят планету содрогнуться.""",
            posterPath = "/19GXuePqcZSPD5JgT9MeVdeu9Tc.jpg",
            backdropPath = "https://image.tmdb.org/t/p/w500//nmGWzTLMXy9x7mKd8NKPLmHtWGa.jpg",
            releaseDate = "2022-06-29",
            title = "Миньоны: Грювитация",
            voteAverage = 7.6F
        )
    )
}