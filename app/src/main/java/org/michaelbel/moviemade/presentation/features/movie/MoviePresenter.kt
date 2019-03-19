package org.michaelbel.moviemade.presentation.features.movie

import io.reactivex.observers.DisposableObserver
import org.michaelbel.moviemade.core.entity.AccountStates
import org.michaelbel.moviemade.core.entity.CreditsResponse
import org.michaelbel.moviemade.core.entity.Crew.Companion.DIRECTING
import org.michaelbel.moviemade.core.entity.Crew.Companion.PRODUCTION
import org.michaelbel.moviemade.core.entity.Crew.Companion.WRITING
import org.michaelbel.moviemade.core.entity.Movie
import org.michaelbel.moviemade.core.net.NetworkUtil
import org.michaelbel.moviemade.presentation.base.Presenter
import java.util.*

class MoviePresenter(
        private val repository: MovieContract.Repository
): Presenter(), MovieContract.Presenter {

    private lateinit var view: MovieContract.View

    override fun attach(view: MovieContract.View) {
        this.view = view
    }

    override fun getDetails(sessionId: String, movieId: Int) {
        if (NetworkUtil.isNetworkConnected().not()) {
            view.setConnectionError()
            return
        }

        disposable.add(repository.getDetails(movieId)
                .subscribeWith(object: DisposableObserver<Movie>() {
                    override fun onNext(movie: Movie) {
                        view.movie(movie)

                        if (movie != null) {
                            if (movie.homepage != null) {
                                view.setURLs(movie.imdbId, movie.homepage)
                            }

                            fixCredits(movie.credits!!)
                            view.showComplete(movie)
                        }
                    }

                    override fun onError(e: Throwable) {
                        view.setConnectionError()
                    }

                    override fun onComplete() {
                        getAccountStates(sessionId, movieId)
                    }
                }))
    }

    override fun markFavorite(sessionId: String, accountId: Int, mediaId: Int, favorite: Boolean) {
        if (NetworkUtil.isNetworkConnected().not()) return

        disposable.add(repository.markFavorite(accountId, sessionId, mediaId, favorite)
                .subscribe({ view.onFavoriteChanged(it) }, { view.setConnectionError() }))
    }

    override fun addWatchlist(sessionId: String, accountId: Int, mediaId: Int, watchlist: Boolean) {
        if (NetworkUtil.isNetworkConnected().not()) return

        disposable.add(repository.addWatchlist(accountId, sessionId, mediaId, watchlist)
                .subscribe({ view.onWatchListChanged(it) }, { view.setConnectionError() }))
    }

    override fun getAccountStates(sessionId: String, movieId: Int) {
        disposable.add(repository.getAccountStates(movieId, sessionId)
                .subscribeWith(object: DisposableObserver<AccountStates>() {
                    override fun onNext(states: AccountStates) {
                        view.setStates(states.favorite, states.watchlist)
                    }

                    override fun onError(e: Throwable) {
                        // Fixme: Rated object has an error.
                    }

                    override fun onComplete() {}
                }))
    }

    private fun fixCredits(credits: CreditsResponse) {
        val actorsBuilder = StringBuilder()
        for (cast in credits.cast) {
            actorsBuilder.append(cast.name)
            if (cast != credits.cast[credits.cast.size - 1]) {
                actorsBuilder.append(", ")
            }
        }

        val directors = ArrayList<String>()
        val writers = ArrayList<String>()
        val producers = ArrayList<String>()
        for (crew in credits.crew) {
            when (crew.department) {
                DIRECTING -> directors.add(crew.name)
                WRITING -> writers.add(crew.name)
                PRODUCTION -> producers.add(crew.name)
            }
        }

        val directorsBuilder = StringBuilder()
        for (i in directors.indices) {
            directorsBuilder.append(directors[i])
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

        view.setCredits(actorsBuilder.toString(), directorsBuilder.toString(),
                writersBuilder.toString(), producersBuilder.toString())
    }

    override fun destroy() {
        disposable.dispose()
    }
}