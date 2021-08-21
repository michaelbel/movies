package org.michaelbel.moviemade.presentation.features.movie

import android.util.SparseArray
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.michaelbel.data.remote.model.AccountStates
import org.michaelbel.data.remote.model.CreditsResponse
import org.michaelbel.data.remote.model.Crew.Companion.DIRECTING
import org.michaelbel.data.remote.model.Crew.Companion.PRODUCTION
import org.michaelbel.data.remote.model.Crew.Companion.WRITING
import org.michaelbel.data.remote.model.Mark
import org.michaelbel.data.remote.model.Movie
import org.michaelbel.data.remote.model.Movie.Companion.CREDITS
import org.michaelbel.domain.MoviesRepository
import org.michaelbel.moviemade.BuildConfig.TMDB_API_KEY
import org.michaelbel.moviemade.core.TmdbConfig.CONTENT_TYPE
import timber.log.Timber
import java.util.ArrayList
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class MovieModel @Inject constructor(
    val repository: MoviesRepository
): ViewModel() {

    var movie = MutableSharedFlow<Movie>()
    var imdb = MutableSharedFlow<String>()
    var homepage = MutableSharedFlow<String>()
    var connectionError = MutableSharedFlow<Any>()
    var favoriteChange = MutableSharedFlow<Mark>()
    var watchlistChange = MutableSharedFlow<Mark>()
    var accountStates = MutableSharedFlow<AccountStates>()
    var credit = MutableSharedFlow<SparseArray<String>>()

    fun movie(sessionId: String, movieId: Long) {
        viewModelScope.launch {
            try {
                val result = repository.movie(movieId, TMDB_API_KEY, Locale.getDefault().language, CREDITS)
                withContext(Dispatchers.Main) {
                    if (result.isSuccessful) {
                        result.body()?.let {
                            movie.emit(result.body()!!)

                            accountStates(sessionId, movieId)

                            it.imdbId?.let { imdbId -> imdb.emit(imdbId) }

                            it.homepage?.let { link -> homepage.emit(link) }

                            it.credits?.let { credits ->
                                fixCredits(credits)
                            }
                        }
                    } else {
                        // todo smth
                    }
                }
            } catch (e: Throwable) {
                Timber.e(e)
            }
        }
    }

    fun markFavorite(sessionId: String, accountId: Long, mediaId: Long, favorite: Boolean) {
        viewModelScope.launch {
            try {
                val result = repository.markFavorite(CONTENT_TYPE, accountId, TMDB_API_KEY, sessionId, mediaId, favorite)
                withContext(Dispatchers.Main) {
                    if (result.isSuccessful) {
                        favoriteChange.emit(result.body()!!)
                    } else {
                        // todo smth
                    }
                }
            } catch (e: Throwable) {
                connectionError.emit(e)
            }
        }
    }

    fun addWatchlist(sessionId: String, accountId: Long, mediaId: Long, watchlist: Boolean) {
        viewModelScope.launch {
            try {
                val result = repository.addWatchlist(CONTENT_TYPE, accountId, sessionId, TMDB_API_KEY, mediaId, watchlist)
                withContext(Dispatchers.Main) {
                    if (result.isSuccessful) {
                        watchlistChange.emit(result.body()!!)
                    } else {
                        // todo smth
                    }
                }
            } catch (e: Throwable) {
                connectionError.emit(e)
            }
        }
    }

    private fun accountStates(sessionId: String, movieId: Long) {
        viewModelScope.launch {
            try {
                val result = repository.accountStates(movieId, TMDB_API_KEY, sessionId)
                withContext(Dispatchers.Main) {
                    if (result.isSuccessful) {
                        accountStates.emit(result.body()!!)
                    } else {
                        // todo smth
                    }
                }
            } catch (e: Throwable) {
                connectionError.emit(e)
            }
        }
    }

    private fun fixCredits(credits: CreditsResponse) {
        val actorsBuilder = StringBuilder()

        credits.cast.forEach {
            actorsBuilder.append(it.name)
            if (it != credits.cast[credits.cast.size - 1]) {
                actorsBuilder.append(", ")
            }
        }

        val directors = ArrayList<String>()
        val writers = ArrayList<String>()
        val producers = ArrayList<String>()

        credits.crew.forEach {
            when (it.department) {
                DIRECTING -> directors.add(it.name)
                WRITING -> writers.add(it.name)
                PRODUCTION -> producers.add(it.name)
            }
        }

        val directorsBuilder = StringBuilder()
        directors.indices.forEach {
            directorsBuilder.append(directors[it])
            if (it != directors.size - 1) {
                directorsBuilder.append(", ")
            }
        }

        val writersBuilder = StringBuilder()
        writers.indices.forEach {
            writersBuilder.append(writers[it])
            if (it != writers.size - 1) {
                writersBuilder.append(", ")
            }
        }

        val producersBuilder = StringBuilder()
        producers.indices.forEach {
            producersBuilder.append(producers[it])
            if (it != producers.size - 1) {
                producersBuilder.append(", ")
            }
        }

        val sparse: SparseArray<String> = SparseArray()
        sparse.put(KEY_ACTORS, actorsBuilder.toString())
        sparse.put(KEY_DIRECTORS, directorsBuilder.toString())
        sparse.put(KEY_WRITERS, writersBuilder.toString())
        sparse.put(KEY_PRODUCERS, producersBuilder.toString())

        viewModelScope.launch { credit.emit(sparse) }
    }

    companion object {
        const val KEY_ACTORS = 0
        const val KEY_DIRECTORS = 1
        const val KEY_WRITERS = 2
        const val KEY_PRODUCERS = 3
    }
}