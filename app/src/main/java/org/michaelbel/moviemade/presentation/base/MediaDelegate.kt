package org.michaelbel.moviemade.presentation.base

import org.michaelbel.moviemade.core.entity.Keyword
import org.michaelbel.moviemade.core.entity.Movie
import org.michaelbel.moviemade.core.entity.Review

interface MediaDelegate {
    fun startMovie(movie: Movie)
    fun startMovies(list: String, movie: Movie)
    fun startMovies(list: String, accountId: Int = 0)
    fun startTrailers(movie: Movie)
    fun startReview(review: Review, movie: Movie)
    fun startReviews(movie: Movie)
    fun startKeyword(keyword: Keyword)
    fun startKeywords(movie: Movie)
}