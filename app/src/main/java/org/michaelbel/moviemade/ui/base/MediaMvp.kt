package org.michaelbel.moviemade.ui.base

import org.michaelbel.moviemade.data.dao.Keyword
import org.michaelbel.moviemade.data.dao.Movie

interface MediaMvp {

    fun startMovie(movie: Movie)

    fun startTrailers(movie: Movie)

    fun startReviews(movie: Movie)

    fun startKeywords(movie: Movie)

    fun startKeyword(keyword: Keyword)
}