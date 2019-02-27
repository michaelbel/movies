package org.michaelbel.moviemade.presentation.base

import android.view.View
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import org.michaelbel.moviemade.core.entity.Keyword
import org.michaelbel.moviemade.core.entity.Movie
import org.michaelbel.moviemade.core.entity.Review

interface BaseContract {

    fun startFragment(fragment: Fragment, @LayoutRes containerId: Int)
    fun startFragment(fragment: Fragment, @LayoutRes containerId: Int, tag: String)
    fun finishFragment()

    interface MediaView {
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
}