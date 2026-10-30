package org.michaelbel.movies.details

import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.michaelbel.movies.common.mvi.Event
import org.michaelbel.movies.common.mvi.MoviesViewModel
import org.michaelbel.movies.details.intent.DetailsIntent
import org.michaelbel.movies.details.model.DetailsModel
import org.michaelbel.movies.domain.usecase.AccountPojoFlowUseCase
import org.michaelbel.movies.domain.usecase.MovieDetailsUseCase
import org.michaelbel.movies.domain.usecase.MovieDetailsUseCase.MovieDetailsException
import org.michaelbel.movies.domain.usecase.MovieFlowUseCase
import org.michaelbel.movies.domain.usecase.TraktMovieDetailsUseCase
import org.michaelbel.movies.domain.usecase.TraktMovieFlowUseCase
import org.michaelbel.movies.domain.usecase.UpdateFavoriteUseCase
import org.michaelbel.movies.interactor.Interactor
import org.michaelbel.movies.interactor.UiInteractor
import org.michaelbel.movies.network.config.ScreenState
import org.michaelbel.movies.network.connectivity.NetworkManager
import org.michaelbel.movies.network.model.Movie
import org.michaelbel.movies.persistence.database.ktx.isNotEmpty
import org.michaelbel.movies.ui.navigation.AuthDestination
import org.michaelbel.movies.ui.navigation.DetailsDestination
import org.michaelbel.movies.ui.navigation.GalleryDestination
import org.michaelbel.movies.ui.navigation.MainNavigator
import org.michaelbel.movies.ui.pending.PendingAction
import org.michaelbel.movies.ui.pending.PendingActionStore

class DetailsViewModel(
    private val destination: DetailsDestination,
    private val interactor: Interactor,
    private val uiInteractor: UiInteractor,
    private val networkManager: NetworkManager,
    private val movieFlowUseCase: MovieFlowUseCase,
    private val accountPojoFlowUseCase: AccountPojoFlowUseCase,
    private val movieDetailsUseCase: MovieDetailsUseCase,
    private val updateFavoriteUseCase: UpdateFavoriteUseCase,
    private val traktMovieFlowUseCase: TraktMovieFlowUseCase,
    private val traktMovieDetailsUseCase: TraktMovieDetailsUseCase
):MoviesViewModel<DetailsModel, DetailsIntent, Event>(DetailsModel()) {

    init {
        dispatch(DetailsIntent.CollectNetworkStatus)
        dispatch(DetailsIntent.CollectFeatureFlags)
        dispatch(DetailsIntent.CollectAccount)
        dispatch(DetailsIntent.CollectFavorite)
        dispatch(DetailsIntent.CollectMovieDb)
        dispatch(DetailsIntent.LoadMovie)
    }

    override fun dispatch(intent: DetailsIntent) {
        when (intent) {
            is DetailsIntent.CollectNetworkStatus -> {
                launch {
                    networkManager.status.collectLatest { networkStatus ->
                        reduce { it.copy(networkStatus = networkStatus) }
                    }
                }
            }
            is DetailsIntent.CollectFeatureFlags -> {
                reduce {
                    it.copy(
                        isDetailsFavoriteFeatureEnabled = uiInteractor.isDetailsFavoriteFeatureEnabled && !destination.isTrakt,
                        isDetailsGalleryFeatureEnabled = uiInteractor.isDetailsGalleryFeatureEnabled && !destination.isTrakt,
                        isDetailsShareFeatureEnabled = uiInteractor.isDetailsShareFeatureEnabled,
                        isTrakt = destination.isTrakt
                    )
                }
            }
            is DetailsIntent.CollectAccount -> {
                launch {
                    accountPojoFlowUseCase(Unit).collectLatest { accountPojo ->
                        reduce { it.copy(isAuthorized = accountPojo.isNotEmpty) }
                    }
                }
            }
            is DetailsIntent.CollectFavorite -> {
                when {
                    destination.isTrakt -> Unit
                    else -> {
                        launch {
                            val params = MovieFlowUseCase.Params(Movie.FAVORITE, destination.movieId)
                            movieFlowUseCase(params).collectLatest { favoriteMovie ->
                                reduce { it.copy(isFavorite = favoriteMovie != null) }
                            }
                        }
                    }
                }
            }
            is DetailsIntent.CollectMovieDb -> {
                launch {
                    val movieFlow = when {
                        destination.isTrakt -> {
                            val params = TraktMovieFlowUseCase.Params(destination.movieList.orEmpty(), destination.movieId)
                            traktMovieFlowUseCase(params)
                        }
                        else -> {
                            val params = MovieFlowUseCase.Params(destination.movieList.orEmpty(), destination.movieId)
                            movieFlowUseCase(params)
                        }
                    }
                    movieFlow.collectLatest { movieDb ->
                        if (movieDb != null) {
                            reduce { it.copy(detailsState = ScreenState.Content(movieDb)) }
                        }
                    }
                }
            }
            is DetailsIntent.LoadMovie -> {
                launch {
                    val movie = when {
                        destination.isTrakt -> {
                            val params = TraktMovieDetailsUseCase.Params(destination.movieList.orEmpty(), destination.movieId)
                            traktMovieDetailsUseCase(params).getOrThrow()
                        }
                        else -> {
                            val params = MovieDetailsUseCase.Params(
                                pagingKey = destination.movieList.orEmpty(),
                                language = interactor.language,
                                movieId = destination.movieId
                            )
                            movieDetailsUseCase(params).getOrThrow()
                        }
                    }
                    reduce { it.copy(detailsState = ScreenState.Content(movie)) }
                }
            }
            is DetailsIntent.BackClick -> launch { MainNavigator.back() }
            is DetailsIntent.GalleryClick -> {
                launch { MainNavigator.forward(GalleryDestination(destination.movieId)) }
            }
            is DetailsIntent.FavoriteClick -> when {
                !stateFlow.value.isAuthorized -> {
                    PendingActionStore.set(PendingAction.AddFavorite(destination.movieId))
                    launch { MainNavigator.forward(AuthDestination) }
                }
                !stateFlow.value.isFavoriteJobActive -> {
                    val params = UpdateFavoriteUseCase.Params(
                        movieId = destination.movieId,
                        favorite = !stateFlow.value.isFavorite
                    )
                    val job = launch { updateFavoriteUseCase(params).getOrThrow() }.also { launchedJob ->
                        launchedJob.invokeOnCompletion { reduce { it.copy(favoriteJob = null) } }
                    }
                    reduce { it.copy(favoriteJob = job) }
                }
            }
        }
    }

    override fun catch(throwable: Throwable) {
        when (throwable) {
            is MovieDetailsException -> {
                reduce { it.copy(detailsState = ScreenState.Failure(throwable)) }
            }
            else -> super.catch(throwable)
        }
    }
}
