package org.michaelbel.movies.details

import androidx.lifecycle.SavedStateHandle
import com.google.android.gms.ads.AdRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.michaelbel.movies.analytics.Analytics
import org.michaelbel.movies.analytics.model.AnalyticsScreen
import org.michaelbel.movies.core.viewmodel.BaseViewModel
import org.michaelbel.movies.details.model.DetailsState
import org.michaelbel.movies.domain.interactor.MovieInteractor
import org.michaelbel.movies.entities.Either
import org.michaelbel.movies.entities.MovieDetailsData
import org.michaelbel.movies.entities.handle

@HiltViewModel
class DetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val movieInteractor: MovieInteractor,
    analytics: Analytics
): BaseViewModel() {

    private val movieId: Long = requireNotNull(savedStateHandle["movieId"])

    val state: Flow<DetailsState> = flow {
        emit(DetailsState.Loading)
        val either: Either<MovieDetailsData> = movieInteractor.movieDetails(movieId)
        either.handle(
            success = { movieDetailsData ->
                emit(DetailsState.Content(movieDetailsData))
            },
            failure = { throwable ->
                emit(DetailsState.Failure(throwable))
            }
        )
    }

    val adRequest: AdRequest = AdRequest.Builder().build()

    init {
        analytics.trackScreen(AnalyticsScreen.DETAILS)
    }
}