package org.michaelbel.moviemade.presentation.features.movie

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import org.michaelbel.data.Movie
import org.michaelbel.data.Movie.Companion.CREDITS
import org.michaelbel.data.remote.model.AccountStates
import org.michaelbel.data.remote.model.CreditsResponse
import org.michaelbel.data.remote.model.Crew
import org.michaelbel.data.remote.model.Mark
import org.michaelbel.domain.MoviesRepository
import org.michaelbel.moviemade.BuildConfig.TMDB_API_KEY
import org.michaelbel.moviemade.core.TmdbConfig.CONTENT_TYPE
import java.util.*
import kotlin.collections.HashMap

class MovieModel(val repository: MoviesRepository): ViewModel() {

    private val disposable = CompositeDisposable()

    interface Listener {
        fun onClick(cell: String)
    }

    lateinit var listener: Listener

    var movie = MutableLiveData<Movie>()
    var imdb = MutableLiveData<String>()
    var homepage = MutableLiveData<String>()
    var connectionError = MutableLiveData<Any>()
    var favoriteChange = MutableLiveData<Mark>()
    var watchlistChange = MutableLiveData<Mark>()
    var accountStates = MutableLiveData<AccountStates>()
    var credit = MutableLiveData<HashMap<String, String>>()

    fun details(sessionId: String, movieId: Int) {
        disposable.add(repository.details(movieId, TMDB_API_KEY, Locale.getDefault().language, CREDITS)
                .subscribeWith(object: DisposableObserver<Movie>() {
                    override fun onNext(it: Movie) {
                        movie.postValue(it)

                        if (it != null) {
                            if (it.homepage != null) {
                                imdb.postValue(it.imdbId)
                                homepage.postValue(it.homepage)
                            }

                            fixCredits(it.credits)
                        }
                    }

                    override fun onError(e: Throwable) {
                        connectionError.postValue(e)
                    }

                    override fun onComplete() {
                        getAccountStates(sessionId, movieId)
                    }
                }))
    }

    fun markFavorite(sessionId: String, accountId: Int, mediaId: Int, favorite: Boolean) {
        disposable.add(repository.markFavorite(CONTENT_TYPE, accountId, TMDB_API_KEY, sessionId, mediaId, favorite)
                .subscribe({ favoriteChange.postValue(it) }, { connectionError.postValue(it) }))
    }

    fun addWatchlist(sessionId: String, accountId: Int, mediaId: Int, watchlist: Boolean) {
        disposable.add(repository.addWatchlist(CONTENT_TYPE, accountId, sessionId, TMDB_API_KEY, mediaId, watchlist)
                .subscribe({ watchlistChange.postValue(it) }, { connectionError.postValue(it) }))
    }

    fun getAccountStates(sessionId: String, movieId: Int) {
        disposable.add(repository.accountStates(movieId, TMDB_API_KEY, sessionId)
                .subscribeWith(object: DisposableObserver<AccountStates>() {
                    override fun onNext(states: AccountStates) {
                        accountStates.postValue(states)
                    }

                    override fun onError(e: Throwable) {
                        // fixme: Rated object has an error
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
                Crew.DIRECTING -> directors.add(crew.name)
                Crew.WRITING -> writers.add(crew.name)
                Crew.PRODUCTION -> producers.add(crew.name)
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

        val map: HashMap<String, String> = HashMap()
        map["actors"] = actorsBuilder.toString()
        map["directors"] = directorsBuilder.toString()
        map["writers"] = writersBuilder.toString()
        map["producers"] = producersBuilder.toString()
        credit.postValue(map)
    }

    override fun onCleared() {
        super.onCleared()
        disposable.dispose()
    }
}