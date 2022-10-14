package org.michaelbel.movies.details

import androidx.lifecycle.SavedStateHandle
import com.google.android.gms.ads.AdRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import org.michaelbel.movies.common.viewmodel.BaseViewModel
import org.michaelbel.movies.domain.usecase.MovieDetailsCase
import org.michaelbel.movies.entities.lce.ScreenState

@HiltViewModel
class DetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val movieDetails: MovieDetailsCase
): BaseViewModel() {

    private val movieId: Long = requireNotNull(savedStateHandle["movieId"])

    val detailsState: StateFlow<ScreenState> = flow {
        emit(movieDetails(movieId))
    }.stateIn(
        scope = this,
        started = SharingStarted.Lazily,
        initialValue = ScreenState.Loading
    )

    val adRequest: AdRequest = AdRequest.Builder().build()
}