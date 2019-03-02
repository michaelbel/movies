package org.michaelbel.moviemade.presentation.base

import org.michaelbel.moviemade.core.entity.Keyword
import org.michaelbel.moviemade.core.entity.Movie
import org.michaelbel.moviemade.core.entity.Review

interface MediaDelegate {
    fun startMovie(movie: Movie)
    fun startTrailers(movie: Movie)
    fun startReviews(movie: Movie)
    fun startReview(review: Review, movie: Movie)
    fun startKeywords(movie: Movie)
    fun startKeyword(keyword: Keyword)
    fun startFavorites(accountId: Int)
    fun startWatchlist(accountId: Int)
    fun startSimilarMovies(movie: Movie)
    fun startRcmdsMovies(movie: Movie)
}