package org.michaelbel.moviemade.ui.base

import android.view.View
import androidx.fragment.app.Fragment
import org.michaelbel.moviemade.data.entity.Keyword
import org.michaelbel.moviemade.data.entity.Movie
import org.michaelbel.moviemade.data.entity.Review

interface BaseContract {

    fun startFragment(fragment: Fragment, container: View)
    fun startFragment(fragment: Fragment, containerId: Int)
    fun startFragment(fragment: Fragment, container: View, tag: String)
    fun startFragment(fragment: Fragment, containerId: Int, tag: String)
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