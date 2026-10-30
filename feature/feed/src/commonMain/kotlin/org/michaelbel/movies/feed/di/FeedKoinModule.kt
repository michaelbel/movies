package org.michaelbel.movies.feed.di

import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import org.michaelbel.movies.domain.usecase.di.useCaseKoinModule
import org.michaelbel.movies.feed.FeedViewModel
import org.michaelbel.movies.interactor.di.interactorKoinModule
import org.michaelbel.movies.network.connectivity.di.connectivityKoinModule

val feedKoinModule = module {
    includes(
        useCaseKoinModule,
        interactorKoinModule,
        connectivityKoinModule
    )
    viewModel {
        FeedViewModel(
            uiInteractor = get(),
            interactor = get(),
            appNotificationInteractor = get(),
            networkManager = get(),
            suggestionPojosFlowUseCase = get(),
            moviesFlowUseCase = get(),
            accountPojoFlowUseCase = get(),
            updateSuggestionsUseCase = get(),
            fetchAndInsertSearchMoviesUseCase = get(),
            movieUseCase = get(),
            currentFeedViewFlowUseCase = get(),
            currentMovieListFlowUseCase = get(),
            providerFlowUseCase = get(),
            currentTraktListFlowUseCase = get(),
            moviesPagingDataUseCase = get(),
            traktMoviesPagingDataUseCase = get(),
            searchMoviesPagingDataUseCase = get(),
            removeMoviesUseCase = get(),
            removeMovieUseCase = get(),
            insertMovieUseCase = get(),
            traktSearchMoviesPagingDataUseCase = get(),
            traktSearchHistoryMoviesFlowUseCase = get(),
            traktSaveSearchHistoryMovieUseCase = get(),
            traktRemoveSearchHistoryMovieUseCase = get(),
            traktClearSearchHistoryUseCase = get()
        )
    }
}
