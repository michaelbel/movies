package org.michaelbel.movies.details.ui.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import org.michaelbel.movies.domain.data.entity.MovieDb
import org.michaelbel.movies.network.model.Movie

internal class MoviePreviewParameterProvider: PreviewParameterProvider<MovieDb> {
    override val values: Sequence<MovieDb> = sequenceOf(
        MovieDb(
            movieList = Movie.NOW_PLAYING,
            dateAdded = System.currentTimeMillis(),
            position = 0,
            movieId = 438148,
            overview = """Queen Ramonda, Shuri, M’Baku, Okoye and the Dora Milaje fight to protect 
                their nation from intervening world powers in the wake of King T’Challa’s death. 
                As the Wakandans strive to embrace their next chapter, the heroes must band 
                together with the help of War Dog Nakia and Everett Ross and forge a new path for 
                the kingdom of Wakanda.""",
            posterPath = "",
            backdropPath = "",
            releaseDate = "2022-11-09",
            title = "Black Panther: Wakanda Forever",
            voteAverage = 7.5F
        )
    )
}