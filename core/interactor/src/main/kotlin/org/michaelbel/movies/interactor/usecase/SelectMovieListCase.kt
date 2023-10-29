package org.michaelbel.movies.interactor.usecase

import org.michaelbel.movies.common.list.MovieList
import org.michaelbel.movies.interactor.Interactor
import javax.inject.Inject

class SelectMovieListCase @Inject constructor(
    private val interactor: Interactor
) {
    suspend operator fun invoke(movieList: MovieList) {
        interactor.selectMovieList(movieList)
    }
}