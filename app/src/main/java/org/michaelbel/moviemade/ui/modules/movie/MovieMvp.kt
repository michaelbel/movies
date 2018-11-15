package org.michaelbel.moviemade.ui.modules.movie

import com.arellomobile.mvp.MvpView
import org.michaelbel.moviemade.data.dao.Cast
import org.michaelbel.moviemade.data.dao.Crew

import org.michaelbel.moviemade.data.dao.Movie

interface MovieMvp : MvpView {

    fun setPoster(posterPath: String)

    fun setMovieTitle(title: String)

    fun setOverview(overview: String)

    fun setVoteAverage(voteAverage: Float)

    fun setVoteCount(voteCount: Int)

    fun setReleaseDate(releaseDate: String)

    fun setOriginalLanguage(originalLanguage: String)

    fun setRuntime(runtime: String)

    fun setTagline(tagline: String)

    fun setURLs(imdbId: String, homepage: String)

    fun setWatching(watch: Boolean)

    fun setCredits(casts: String, directors: String, writers: String, producers: String)

    fun setConnectionError()

    fun showComplete(movie: Movie)
}