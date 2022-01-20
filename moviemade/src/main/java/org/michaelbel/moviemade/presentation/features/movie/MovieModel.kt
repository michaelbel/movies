package org.michaelbel.moviemade.presentation.features.movie

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import org.michaelbel.moviemade.data.model.Movie
import org.michaelbel.moviemade.BuildConfig.TMDB_API_KEY
import org.michaelbel.moviemade.domain.repo.MoviesRepository
import java.util.*

class MovieModel @AssistedInject constructor(
    @Assisted val movieId: Int,
    private val repository: MoviesRepository
): ViewModel() {

    val movieFlow = MutableSharedFlow<Movie>()

    init {
        loadDetails()
    }

    private fun loadDetails() = viewModelScope.launch {
        runCatching {
            val result = repository.movie(movieId.toLong(), TMDB_API_KEY, Locale.getDefault().language)
            if (result.isSuccessful) {
                val movie: Movie? = result.body()
                movie?.let { movieFlow.emit(it) }
            }
        }.onFailure {}
    }

   /* fun markFavorite(sessionId: String, accountId: Long, mediaId: Long, favorite: Boolean) {
        viewModelScope.launch {
            try {
                val result = repository.markFavorite(CONTENT_TYPE, accountId, TMDB_API_KEY, sessionId, mediaId, favorite)
                withContext(Dispatchers.Main) {
                    if (result.isSuccessful) {

                    } else {
                        // todo smth
                    }
                }
            } catch (e: Throwable) {

            }
        }
    }

    fun addWatchlist(sessionId: String, accountId: Long, mediaId: Long, watchlist: Boolean) {
        viewModelScope.launch {
            try {
                val result = repository.addWatchlist(CONTENT_TYPE, accountId, sessionId, TMDB_API_KEY, mediaId, watchlist)
                withContext(Dispatchers.Main) {
                    if (result.isSuccessful) {

                    } else {
                        // todo smth
                    }
                }
            } catch (e: Throwable) {

            }
        }
    }

    private fun accountStates(sessionId: String, movieId: Long) {
        viewModelScope.launch {
            try {
                val result = repository.accountStates(movieId, TMDB_API_KEY, sessionId)
                withContext(Dispatchers.Main) {
                    if (result.isSuccessful) {

                    } else {
                        // todo smth
                    }
                }
            } catch (e: Throwable) {

            }
        }
    }*/

    @AssistedFactory
    interface Factory {
        fun create(@Assisted movieId: Int): MovieModel
    }
}