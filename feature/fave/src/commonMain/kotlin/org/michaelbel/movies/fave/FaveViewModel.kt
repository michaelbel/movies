package org.michaelbel.movies.fave

import androidx.paging.PagingData
import androidx.paging.cachedIn
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.michaelbel.movies.common.mvi.MoviesViewModel
import org.michaelbel.movies.domain.usecase.CurrentFeedViewFlowUseCase
import org.michaelbel.movies.domain.usecase.FavoriteMoviesPagingDataUseCase
import org.michaelbel.movies.fave.event.FaveEvent
import org.michaelbel.movies.fave.intent.FaveIntent
import org.michaelbel.movies.fave.model.FaveModel
import org.michaelbel.movies.interactor.Interactor
import org.michaelbel.movies.interactor.UiInteractor
import org.michaelbel.movies.persistence.database.entity.pojo.MoviePojo
import org.michaelbel.movies.ui.navigation.DetailsDestination
import org.michaelbel.movies.ui.navigation.MainNavigator

class FaveViewModel(
    private val uiInteractor: UiInteractor,
    interactor: Interactor,
    private val currentFeedViewFlowUseCase: CurrentFeedViewFlowUseCase,
    favoriteMoviesPagingDataUseCase: FavoriteMoviesPagingDataUseCase
): MoviesViewModel<FaveModel, FaveIntent, FaveEvent>(FaveModel()) {

    val pagingDataFlow: Flow<PagingData<MoviePojo>> = favoriteMoviesPagingDataUseCase(interactor.language)
        .cachedIn(this)

    init {
        dispatch(FaveIntent.CollectFeedView)
        dispatch(FaveIntent.CollectPageFailureButtonVisible)
    }

    override fun dispatch(intent: FaveIntent) {
        when (intent) {
            is FaveIntent.CollectFeedView -> {
                launch {
                    currentFeedViewFlowUseCase(Unit).collectLatest { feedView ->
                        reduce { it.copy(feedView = feedView) }
                    }
                }
            }
            is FaveIntent.CollectPageFailureButtonVisible -> {
                reduce { it.copy(isPageFailureButtonVisible = uiInteractor.isPageFailureButtonVisible) }
            }
            is FaveIntent.ScrollToTop -> {
                launch { push(FaveEvent.ScrollToTop) }
            }
            is FaveIntent.MovieDetailsClick -> {
                launch { MainNavigator.forward(DetailsDestination(intent.pagingKey, intent.movieId)) }
            }
        }
    }
}
