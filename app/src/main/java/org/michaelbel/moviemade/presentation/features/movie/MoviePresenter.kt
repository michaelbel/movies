package org.michaelbel.moviemade.presentation.features.movie

import android.content.SharedPreferences
import io.reactivex.observers.DisposableObserver
import org.michaelbel.moviemade.core.entity.AccountStates
import org.michaelbel.moviemade.core.entity.CreditsResponse
import org.michaelbel.moviemade.core.entity.Crew
import org.michaelbel.moviemade.core.entity.Movie
import org.michaelbel.moviemade.core.remote.AccountService
import org.michaelbel.moviemade.core.remote.MoviesService
import org.michaelbel.moviemade.core.utils.AndroidUtil
import org.michaelbel.moviemade.core.utils.DateUtil
import org.michaelbel.moviemade.core.utils.KEY_SESSION_ID
import org.michaelbel.moviemade.core.utils.NetworkUtil
import org.michaelbel.moviemade.presentation.base.Presenter
import java.util.*

class MoviePresenter internal constructor(
        moviesService: MoviesService,
        accountService: AccountService,
        private val preferences: SharedPreferences
): Presenter(), MovieContract.Presenter {

    private var view: MovieContract.View? = null
    private val repository: MovieContract.Repository = MovieRepository(moviesService, accountService)

    override fun attach(view: MovieContract.View) {
        this.view = view
    }

    override fun setDetailExtra(movie: Movie) {
        view?.setPoster(movie.posterPath)
        view?.setMovieTitle(movie.title)
        view?.setOverview(movie.overview)
        view?.setVoteAverage(movie.voteAverage)
        view?.setVoteCount(movie.voteCount)
        view?.setReleaseDate(DateUtil.formatReleaseDate(movie.releaseDate))
        view?.setGenres(movie.genreIds)
    }

    override fun getDetails(movieId: Int) {
        if (!NetworkUtil.isNetworkConnected()) {
            view?.setConnectionError()
            return
        }

        disposable.add(repository.getDetails(movieId)
                .subscribeWith(object: DisposableObserver<Movie>() {
                    override fun onNext(movie: Movie) {
                        view?.setRuntime(DateUtil.formatRuntime(movie.runtime))
                        view?.setOriginalLanguage(AndroidUtil.formatCountries(movie.countries))
                        view?.setTagline(movie.tagline)

                        if (movie != null) {
                            if (movie.homepage != null) {
                                view?.setURLs(movie.imdbId, movie.homepage)
                            }

                            fixCredits(movie.credits!!)
                            view?.showComplete(movie)
                        }
                    }

                    override fun onError(e: Throwable) {
                        view?.setConnectionError()
                    }

                    override fun onComplete() {
                        getAccountStates(movieId)
                    }
                }))
    }

    override fun markFavorite(accountId: Int, mediaId: Int, favorite: Boolean) {
        if (NetworkUtil.isNetworkConnected().not()) return

        disposable.add(repository.markFavorite(accountId, preferences.getString(KEY_SESSION_ID, "")!!, mediaId, favorite)
                .subscribe({ mark -> view?.onFavoriteChanged(mark) }, { view?.setConnectionError() }))
    }

    override fun addWatchlist(accountId: Int, mediaId: Int, watchlist: Boolean) {
        if (NetworkUtil.isNetworkConnected().not()) return

        disposable.add(repository.addWatchlist(accountId, preferences.getString(KEY_SESSION_ID, "")!!, mediaId, watchlist)
                .subscribe({ mark -> view?.onWatchListChanged(mark) }, { view?.setConnectionError() }))
    }

    override fun getAccountStates(movieId: Int) {
        disposable.add(repository.getAccountStates(movieId, preferences.getString(KEY_SESSION_ID, "")!!)
                .subscribeWith(object: DisposableObserver<AccountStates>() {
                    override fun onNext(states: AccountStates) {
                        view?.setStates(states.favorite, states.watchlist)
                    }

                    override fun onError(e: Throwable) {
                        // Fixme: Rated object has an error.
                    }

                    override fun onComplete() {}
                }))
    }

    private fun fixCredits(creditsResponse: CreditsResponse) {
        val actors = ArrayList<String>()
        for (cast in creditsResponse.cast) {
            actors.add(cast.name)
        }
        val actorsBuilder = StringBuilder()
        for (name in actors) {
            actorsBuilder.append(name)
            if (name != actors[actors.size - 1]) {
                actorsBuilder.append(", ")
            }
        }

        val directors = ArrayList<String>()
        val writers = ArrayList<String>()
        val producers = ArrayList<String>()
        for (crew in creditsResponse.crew) {
            when (crew.department) {
                Crew.Credits.DIRECTING -> directors.add(crew.name)
                Crew.Credits.WRITING -> writers.add(crew.name)
                Crew.Credits.PRODUCTION -> producers.add(crew.name)
            }
        }

        val directorsBuilder = StringBuilder()
        for (i in directors.indices) {
            directorsBuilder.append(directors[i])
            // if item is not last and is not empty
            if (i != directors.size - 1) {
                directorsBuilder.append(", ")
            }
        }

        val writersBuilder = StringBuilder()
        for (i in writers.indices) {
            writersBuilder.append(writers[i])
            if (i != writers.size - 1) {
                writersBuilder.append(", ")
            }
        }

        val producersBuilder = StringBuilder()
        for (i in producers.indices) {
            producersBuilder.append(producers[i])
            if (i != producers.size - 1) {
                producersBuilder.append(", ")
            }
        }

        view?.setCredits(
                actorsBuilder.toString(),
                directorsBuilder.toString(),
                writersBuilder.toString(),
                producersBuilder.toString()
        )
    }

    override fun destroy() {
        disposable.dispose()
    }
}