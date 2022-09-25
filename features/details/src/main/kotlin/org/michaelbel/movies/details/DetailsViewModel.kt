package org.michaelbel.movies.details

import androidx.annotation.WorkerThread
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import org.michaelbel.movies.domain.repository.MovieRepository
import org.michaelbel.movies.entities.MovieDetailsData

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val movieRepository: MovieRepository
): ViewModel() {

    private val movieIdSharedFlow: MutableSharedFlow<Long> = MutableSharedFlow(replay = 1)

    val movieFlow: Flow<MovieDetailsData> = movieIdSharedFlow.flatMapLatest(::loadMovieById)

    @WorkerThread
    private fun loadMovieById(movieId: Long): Flow<MovieDetailsData> = flow {
        emit(movieRepository.movieDetails(movieId))
    }.flowOn(Dispatchers.IO)

    fun fetchMovieById(id: Long): Boolean = movieIdSharedFlow.tryEmit(id)
}