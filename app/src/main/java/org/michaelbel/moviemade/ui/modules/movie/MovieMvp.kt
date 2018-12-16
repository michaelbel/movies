package org.michaelbel.moviemade.ui.modules.movie

import com.arellomobile.mvp.MvpView
import org.michaelbel.moviemade.data.entity.Mark
import org.michaelbel.moviemade.data.entity.Movie

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

    fun setStates(fave: Boolean, watch : Boolean)

    fun onFavoriteChanged(mark: Mark)

    fun onWatchListChanged(mark: Mark)

    fun setCredits(casts: String, directors: String, writers: String, producers: String)

    fun setConnectionError()

    fun setGenres(genres : List<Int>)

    fun showComplete(movie: Movie)
}