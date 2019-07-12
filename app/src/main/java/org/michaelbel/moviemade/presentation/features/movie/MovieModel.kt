package org.michaelbel.moviemade.presentation.features.movie

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.michaelbel.data.Movie
import org.michaelbel.data.Movie.Companion.CREDITS
import org.michaelbel.data.remote.model.AccountStates
import org.michaelbel.data.remote.model.CreditsResponse
import org.michaelbel.data.remote.model.Crew.Companion.DIRECTING
import org.michaelbel.data.remote.model.Crew.Companion.PRODUCTION
import org.michaelbel.data.remote.model.Crew.Companion.WRITING
import org.michaelbel.data.remote.model.Mark
import org.michaelbel.domain.MoviesRepository
import org.michaelbel.moviemade.BuildConfig.TMDB_API_KEY
import org.michaelbel.moviemade.core.TmdbConfig.CONTENT_TYPE
import org.michaelbel.moviemade.presentation.App
import timber.log.Timber
import java.util.*
import kotlin.collections.HashMap

class MovieModel(val repository: MoviesRepository): ViewModel() {

    var movie = MutableLiveData<Movie>()
    var imdb = MutableLiveData<String>()
    var homepage = MutableLiveData<String>()
    var connectionError = MutableLiveData<Any>()
    var favoriteChange = MutableLiveData<Mark>()
    var watchlistChange = MutableLiveData<Mark>()
    var accountStates = MutableLiveData<AccountStates>()
    var credit = MutableLiveData<HashMap<String, String>>()

    fun movie(sessionId: String, movieId: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val result = repository.movie(movieId, TMDB_API_KEY, Locale.getDefault().language, CREDITS)
                withContext(Dispatchers.Main) {
                    if (result.isSuccessful) {
                        result.body()?.let {
                            movie.postValue(result.body())

                            it.imdbId?.let { imdbId ->
                                imdb.postValue(imdbId)
                            }

                            it.homepage?.let { link ->
                                homepage.postValue(link)
                            }

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

    fun markFavorite(sessionId: String, accountId: Int, mediaId: Int, favorite: Boolean) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val result = repository.markFavorite(CONTENT_TYPE, accountId, TMDB_API_KEY, sessionId, mediaId, favorite)
                withContext(Dispatchers.Main) {
                    if (result.isSuccessful) {
                        favoriteChange.postValue(result.body())
                    } else {
                        // todo smth
                    }
                }
            } catch (e: Throwable) {
                connectionError.postValue(e)
            }
        }
    }

    fun addWatchlist(sessionId: String, accountId: Int, mediaId: Int, watchlist: Boolean) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val result = repository.addWatchlist(CONTENT_TYPE, accountId, sessionId, TMDB_API_KEY, mediaId, watchlist)
                withContext(Dispatchers.Main) {
                    if (result.isSuccessful) {
                        watchlistChange.postValue(result.body())
                    } else {
                        // todo smth
                    }
                }
            } catch (e: Throwable) {
                connectionError.postValue(e)
            }
        }
    }

    private fun accountStates(sessionId: String, movieId: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val result = repository.accountStates(movieId, TMDB_API_KEY, sessionId)
                withContext(Dispatchers.Main) {
                    if (result.isSuccessful) {
                        App.e("account states post: ${result.body()}")
                        accountStates.postValue(result.body())
                    } else {
                        App.e("account states post error")
                        // todo smth
                    }
                }
            } catch (e: Throwable) {
                App.e("account states throwable: $e")
                connectionError.postValue(e)
            }
        }
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

        val map: HashMap<String, String> = HashMap()
        map["actors"] = actorsBuilder.toString()
        map["directors"] = directorsBuilder.toString()
        map["writers"] = writersBuilder.toString()
        map["producers"] = producersBuilder.toString()
        credit.postValue(map)
    }
}