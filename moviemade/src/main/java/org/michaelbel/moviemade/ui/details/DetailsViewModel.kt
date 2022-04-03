package org.michaelbel.moviemade.ui.details

import androidx.annotation.WorkerThread
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.Locale
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import org.michaelbel.moviemade.BuildConfig
import org.michaelbel.moviemade.app.data.Api
import org.michaelbel.moviemade.app.data.model.Movie

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val api: Api
): ViewModel() {

    private val movieIdSharedFlow: MutableSharedFlow<Long> = MutableSharedFlow(replay = 1)

    val movieFlow: Flow<Movie> = movieIdSharedFlow.flatMapLatest {
        loadMovieById(it)
    }

    @WorkerThread
    private fun loadMovieById(movieId: Long): Flow<Movie> = flow {
        val movie = api.movie(movieId, BuildConfig.TMDB_API_KEY, Locale.getDefault().language)
        emit(movie)
    }.flowOn(Dispatchers.IO)

    fun fetchMovieById(id: Long): Boolean = movieIdSharedFlow.tryEmit(id)
}