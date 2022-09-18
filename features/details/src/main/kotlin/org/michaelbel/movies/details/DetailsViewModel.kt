package org.michaelbel.movies.details

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
import org.michaelbel.movies.core.Api
import org.michaelbel.movies.core.model.Movie

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val api: Api
): ViewModel() {

    private val movieIdSharedFlow: MutableSharedFlow<Long> = MutableSharedFlow(replay = 1)

    val movieFlow: Flow<Movie> = movieIdSharedFlow.flatMapLatest(::loadMovieById)

    @WorkerThread
    private fun loadMovieById(movieId: Long): Flow<Movie> = flow {
        val movie = api.movie(movieId, "5a24c1bdde77b396b0af765355007f45", Locale.getDefault().language)
        emit(movie)
    }.flowOn(Dispatchers.IO)

    fun fetchMovieById(id: Long): Boolean = movieIdSharedFlow.tryEmit(id)
}