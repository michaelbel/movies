@file:OptIn(ExperimentalCoroutinesApi::class)

package org.michaelbel.movies.feed

import androidx.paging.PagingData
import androidx.paging.cachedIn
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import org.michaelbel.movies.common.log.log
import org.michaelbel.movies.common.mvi.MoviesViewModel
import org.michaelbel.movies.domain.usecase.AccountPojoFlowUseCase
import org.michaelbel.movies.domain.usecase.CurrentFeedViewFlowUseCase
import org.michaelbel.movies.domain.usecase.CurrentMovieListFlowUseCase
import org.michaelbel.movies.domain.usecase.CurrentTraktListFlowUseCase
import org.michaelbel.movies.domain.usecase.FetchAndInsertSearchMoviesUseCase
import org.michaelbel.movies.domain.usecase.InsertMovieUseCase
import org.michaelbel.movies.domain.usecase.MoviesFlowUseCase
import org.michaelbel.movies.domain.usecase.MoviesPagingDataUseCase
import org.michaelbel.movies.domain.usecase.MovieUseCase
import org.michaelbel.movies.domain.usecase.ProviderFlowUseCase
import org.michaelbel.movies.domain.usecase.RemoveMovieUseCase
import org.michaelbel.movies.domain.usecase.RemoveMoviesUseCase
import org.michaelbel.movies.domain.usecase.SearchMoviesPagingDataUseCase
import org.michaelbel.movies.domain.usecase.SuggestionPojosFlowUseCase
import org.michaelbel.movies.domain.usecase.TraktMoviesPagingDataUseCase
import org.michaelbel.movies.domain.usecase.UpdateSuggestionsUseCase
import org.michaelbel.movies.feed.event.FeedEvent
import org.michaelbel.movies.feed.intent.FeedIntent
import org.michaelbel.movies.feed.model.FeedModel
import org.michaelbel.movies.interactor.AppNotificationInteractor
import org.michaelbel.movies.interactor.Interactor
import org.michaelbel.movies.interactor.UiInteractor
import org.michaelbel.movies.interactor.entity.AppProvider
import org.michaelbel.movies.network.connectivity.NetworkManager
import org.michaelbel.movies.persistence.database.entity.pojo.MoviePojo
import org.michaelbel.movies.ui.navigation.AccountDestination
import org.michaelbel.movies.ui.navigation.AuthDestination
import org.michaelbel.movies.ui.navigation.DetailsDestination
import org.michaelbel.movies.ui.navigation.MainNavigator
import org.michaelbel.movies.ui.navigation.NotifyDestination
import org.michaelbel.movies.ui.navigation.SettingsDestination
import org.michaelbel.movies.ui.pending.PendingActionStore

class FeedViewModel(
    private val uiInteractor: UiInteractor,
    private val interactor: Interactor,
    private val appNotificationInteractor: AppNotificationInteractor,
    private val networkManager: NetworkManager,
    private val suggestionPojosFlowUseCase: SuggestionPojosFlowUseCase,
    private val moviesFlowUseCase: MoviesFlowUseCase,
    private val accountPojoFlowUseCase: AccountPojoFlowUseCase,
    private val updateSuggestionsUseCase: UpdateSuggestionsUseCase,
    private val fetchAndInsertSearchMoviesUseCase: FetchAndInsertSearchMoviesUseCase,
    private val movieUseCase: MovieUseCase,
    private val currentFeedViewFlowUseCase: CurrentFeedViewFlowUseCase,
    private val currentMovieListFlowUseCase: CurrentMovieListFlowUseCase,
    private val providerFlowUseCase: ProviderFlowUseCase,
    private val currentTraktListFlowUseCase: CurrentTraktListFlowUseCase,
    private val moviesPagingDataUseCase: MoviesPagingDataUseCase,
    private val traktMoviesPagingDataUseCase: TraktMoviesPagingDataUseCase,
    private val searchMoviesPagingDataUseCase: SearchMoviesPagingDataUseCase,
    private val removeMoviesUseCase: RemoveMoviesUseCase,
    private val removeMovieUseCase: RemoveMovieUseCase,
    private val insertMovieUseCase: InsertMovieUseCase
): MoviesViewModel<FeedModel, FeedIntent, FeedEvent>(FeedModel()) {

    private val _searchQuery: MutableStateFlow<String> = MutableStateFlow("")
    private val searchQuery: StateFlow<String> get() = _searchQuery.asStateFlow()
    private var searchFallbackJob: Job? = null

    val pagingDataFlow: Flow<PagingData<MoviePojo>> = providerFlowUseCase(Unit)
        .map(AppProvider::transform)
        .distinctUntilChanged()
        .flatMapLatest { provider ->
            when (provider) {
                is AppProvider.Tmdb -> {
                    currentMovieListFlowUseCase(Unit)
                        .distinctUntilChanged()
                        .flatMapLatest { movieList ->
                            moviesPagingDataUseCase(MoviesPagingDataUseCase.Params(movieList, interactor.language))
                        }
                }
                is AppProvider.Trakt -> {
                    currentTraktListFlowUseCase(Unit)
                        .distinctUntilChanged()
                        .flatMapLatest { traktList -> traktMoviesPagingDataUseCase(traktList) }
                }
            }
        }
        .cachedIn(this)

    val searchPagingDataFlow: Flow<PagingData<MoviePojo>> = searchQuery
        .flatMapLatest { query ->
            searchMoviesPagingDataUseCase(SearchMoviesPagingDataUseCase.Params(query, interactor.language))
        }
        .cachedIn(this)

    init {
        dispatch(FeedIntent.CollectAccountPojo)
        dispatch(FeedIntent.CollectFeedView)
        dispatch(FeedIntent.CollectMovieList)
        dispatch(FeedIntent.CollectNetworkStatus)
        dispatch(FeedIntent.CollectPageFailureButtonVisible)
        dispatch(FeedIntent.CollectFeatureFlags)
        dispatch(FeedIntent.CollectSuggestions)
        dispatch(FeedIntent.CollectSearchHistoryMovies)
        dispatch(FeedIntent.LoadSuggestions)
        dispatch(FeedIntent.SubscribeNotificationsPermissionRequired)
    }

    override fun dispatch(intent: FeedIntent) {
        when (intent) {
            is FeedIntent.CollectAccountPojo -> {
                launch {
                    accountPojoFlowUseCase(Unit).collectLatest { pojo ->
                        reduce { it.copy(accountPojo = pojo) }
                    }
                }
            }
            is FeedIntent.CollectFeedView -> {
                launch {
                    currentFeedViewFlowUseCase(Unit).collectLatest { feedView ->
                        reduce { it.copy(feedView = feedView) }
                    }
                }
            }
            is FeedIntent.CollectMovieList -> {
                launch {
                    currentMovieListFlowUseCase(Unit).collectLatest { movieList ->
                        reduce { it.copy(movieList = movieList) }
                    }
                }
            }
            is FeedIntent.CollectNetworkStatus -> {
                launch {
                    networkManager.status.collectLatest { networkStatus ->
                        reduce { it.copy(networkStatus = networkStatus) }
                    }
                }
            }
            is FeedIntent.CollectPageFailureButtonVisible -> {
                reduce { it.copy(isPageFailureButtonVisible = uiInteractor.isPageFailureButtonVisible) }
            }
            is FeedIntent.CollectFeatureFlags -> {
                reduce {
                    it.copy(
                        isFeedAuthIconFeatureEnabled = uiInteractor.isFeedAuthIconFeatureEnabled,
                        isFeedVoiceInputFeatureEnabled = uiInteractor.isFeedVoiceInputFeatureEnabled
                    )
                }
            }
            is FeedIntent.CollectSuggestions -> {
                launch {
                    suggestionPojosFlowUseCase(Unit).collectLatest { pojos ->
                        reduce { it.copy(suggestionPojos = pojos) }
                    }
                }
            }
            is FeedIntent.CollectSearchHistoryMovies -> {
                launch {
                    val params = MoviesFlowUseCase.Params(MoviePojo.MOVIES_SEARCH_HISTORY, Int.MAX_VALUE)
                    moviesFlowUseCase(params).collectLatest { pojos ->
                        reduce { it.copy(searchHistoryMoviePojos = pojos) }
                    }
                }
            }
            is FeedIntent.LoadSuggestions -> {
                launch { updateSuggestionsUseCase(interactor.language).getOrThrow() }
            }
            is FeedIntent.SubscribeNotificationsPermissionRequired -> {
                launch {
                    if (appNotificationInteractor.notificationsPermissionRequired()) {
                        MainNavigator.forward(NotifyDestination)
                    }
                }
            }
            is FeedIntent.SettingsClick -> launch { MainNavigator.forward(SettingsDestination) }
            is FeedIntent.AuthClick -> {
                PendingActionStore.clear()
                launch { MainNavigator.forward(AuthDestination) }
            }
            is FeedIntent.AccountClick -> launch { MainNavigator.forward(AccountDestination) }
            is FeedIntent.ClearSearchHistoryClick -> {
                launch { removeMoviesUseCase(MoviePojo.MOVIES_SEARCH_HISTORY).getOrThrow() }
            }
            is FeedIntent.RemoveMovieFromHistoryClick -> {
                launch {
                    val params = RemoveMovieUseCase.Params(MoviePojo.MOVIES_SEARCH_HISTORY, intent.movieId)
                    removeMovieUseCase(params).getOrThrow()
                }
            }
            is FeedIntent.SaveMovieToSearchHistoryClick -> {
                launch {
                    val params = MovieUseCase.Params(searchQuery.value, intent.movieId)
                    val movie = movieUseCase(params).getOrThrow()
                    val insertParams = InsertMovieUseCase.Params(MoviePojo.MOVIES_SEARCH_HISTORY, movie)
                    insertMovieUseCase(insertParams).getOrThrow()
                }
            }
            is FeedIntent.EnterSearchQuery -> { _searchQuery.value = intent.query }
            is FeedIntent.ScrollToTop -> launch { push(FeedEvent.ScrollToTop) }
            is FeedIntent.ShowSnackbar -> {
                launch { push(FeedEvent.ShowSnackbar(intent.message, intent.isLong)) }
            }
            is FeedIntent.MovieDetailsClick -> {
                launch { MainNavigator.forward(DetailsDestination(intent.pagingKey, intent.movieId)) }
            }
        }
    }

    fun refreshSearchMovies(query: String) {
        searchFallbackJob?.cancel()

        when {
            query.isBlank() -> reduce { it.copy(isSearchLoading = false, searchFailure = null) }
            else -> {
                searchFallbackJob = launch {
                    reduce { it.copy(isSearchLoading = true, searchFailure = null) }
                    try {
                        val params = FetchAndInsertSearchMoviesUseCase.Params(
                            query = query,
                            language = interactor.language
                        )
                        fetchAndInsertSearchMoviesUseCase(params).getOrThrow()
                        reduce { it.copy(isSearchLoading = false, searchFailure = null) }
                    } catch (throwable: Throwable) {
                        when (throwable) {
                            is CancellationException -> throw throwable
                            else -> reduce { it.copy(isSearchLoading = false, searchFailure = throwable) }
                        }
                    }
                }
            }
        }
    }
}
