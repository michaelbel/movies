package org.michaelbel.movies.domain.repository

import java.util.Locale
import javax.inject.Inject
import org.michaelbel.movies.domain.datasource.MovieApi
import org.michaelbel.movies.entities.MovieData
import org.michaelbel.movies.entities.MovieDetailsData
import org.michaelbel.movies.entities.mapper.MovieMapper
import org.michaelbel.movies.network.model.Movie
import org.michaelbel.movies.network.model.MovieResponse
import org.michaelbel.movies.network.model.Result

class MovieRepository @Inject constructor(
    private val movieApi: MovieApi,
    private val movieMapper: MovieMapper
) {

    suspend fun movieList(list: String, page: Int): Pair<List<MovieData>, Int> {
        val result: Result<MovieResponse> = movieApi.movies(
            list = list,
            apiKey = "5a24c1bdde77b396b0af765355007f45",
            language = Locale.getDefault().language,
            page = page
        )
        return movieMapper.mapToMovieDataList(result.results) to result.totalPages
    }

    suspend fun movieDetails(id: Long): MovieDetailsData {
        val movie: Movie = movieApi.movie(
            id = id,
            apiKey = "5a24c1bdde77b396b0af765355007f45",
            language = Locale.getDefault().language
        )
        return movieMapper.mapToMovieDetailsData(movie)
    }
}