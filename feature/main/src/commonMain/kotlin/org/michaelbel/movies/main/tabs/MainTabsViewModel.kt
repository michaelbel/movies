package org.michaelbel.movies.main.tabs

import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.michaelbel.movies.common.mvi.MoviesViewModel
import org.michaelbel.movies.domain.usecase.AccountDetailsUseCase
import org.michaelbel.movies.domain.usecase.AccountDetailsUseCase.AccountDetailsException
import org.michaelbel.movies.domain.usecase.AccountPojoFlowUseCase
import org.michaelbel.movies.domain.usecase.CreateSessionUseCase
import org.michaelbel.movies.domain.usecase.CreateSessionUseCase.CreateSessionException
import org.michaelbel.movies.domain.usecase.UpdateFavoriteUseCase
import org.michaelbel.movies.fave.event.FaveEvent
import org.michaelbel.movies.fave.event.FaveEventManager
import org.michaelbel.movies.feed.event.FeedEvent
import org.michaelbel.movies.feed.event.FeedEventManager
import org.michaelbel.movies.interactor.Interactor
import org.michaelbel.movies.interactor.UiInteractor
import org.michaelbel.movies.main.event.MainEvent
import org.michaelbel.movies.main.tabs.event.MainTabsEvent
import org.michaelbel.movies.main.tabs.event.MainTabsEventManager
import org.michaelbel.movies.main.tabs.intent.MainTabsIntent
import org.michaelbel.movies.main.tabs.model.MainTabsModel
import org.michaelbel.movies.persistence.database.ktx.isNotEmpty
import org.michaelbel.movies.settings.event.SettingsEvent
import org.michaelbel.movies.settings.event.SettingsEventManager
import org.michaelbel.movies.ui.navigation.AccountDestination
import org.michaelbel.movies.ui.navigation.AuthDestination
import org.michaelbel.movies.ui.navigation.MainNavigator
import org.michaelbel.movies.ui.pending.PendingAction
import org.michaelbel.movies.ui.pending.PendingActionStore
import org.michaelbel.movies.ui.strings.MoviesStrings

class MainTabsViewModel(
    private val interactor: Interactor,
    private val uiInteractor: UiInteractor,
    private val accountPojoFlowUseCase: AccountPojoFlowUseCase,
    private val accountDetailsUseCase: AccountDetailsUseCase,
    private val createSessionUseCase: CreateSessionUseCase,
    private val updateFavoriteUseCase: UpdateFavoriteUseCase
): MoviesViewModel<MainTabsModel, MainTabsIntent, MainTabsEvent>(MainTabsModel()) {

    init {
        dispatch(MainTabsIntent.CollectFaveFeatureEnabled)
        dispatch(MainTabsIntent.CollectAuthorizedState)
    }

    override fun dispatch(intent: MainTabsIntent) {
        when (intent) {
            is MainTabsIntent.CollectFaveFeatureEnabled -> {
                reduce { it.copy(isFaveFeatureEnabled = uiInteractor.isFaveFeatureEnabled) }
            }
            is MainTabsIntent.CollectAuthorizedState -> {
                launch {
                    accountPojoFlowUseCase(Unit).collectLatest { pojo ->
                        val isAuthorized = pojo.isNotEmpty
                        if (isAuthorized && !stateFlow.value.isAuthorized) {
                            when (val pendingAuthAction = PendingActionStore.action) {
                                PendingAction.OpenFave -> {
                                    PendingActionStore.clear()
                                    MainTabsEventManager.push(MainEvent.OpenFave)
                                }
                                is PendingAction.AddFavorite -> {
                                    PendingActionStore.clear()
                                    val params = UpdateFavoriteUseCase.Params(
                                        movieId = pendingAuthAction.movieId,
                                        favorite = true
                                    )
                                    updateFavoriteUseCase(params).getOrThrow()
                                }
                                else -> Unit
                            }
                        }
                        reduce { it.copy(isAuthorized = isAuthorized, accountPojo = pojo) }
                    }
                }
            }
            is MainTabsIntent.HandleRedirect -> {
                when {
                    intent.requestToken == null || intent.approved == null -> Unit
                    !intent.approved -> {
                        PendingActionStore.clear()
                        launch {
                            push(MainTabsEvent.ShowSnackbar(MoviesStrings.feed_auth_failure))
                            MainNavigator.back()
                        }
                    }
                    else -> dispatch(MainTabsIntent.AuthorizeAccount(intent.requestToken))
                }
            }
            is MainTabsIntent.AuthorizeAccount -> {
                launch {
                    createSessionUseCase(intent.requestToken).getOrThrow()
                    accountDetailsUseCase(Unit).getOrThrow()
                    push(MainTabsEvent.ShowSnackbar(MoviesStrings.feed_auth_success))
                    MainNavigator.back()
                }
            }
            is MainTabsIntent.FeedClick -> {
                launch { FeedEventManager.push(FeedEvent.ReselectFeed) }
                launch { MainTabsEventManager.push(MainEvent.OpenFeed) }
            }
            is MainTabsIntent.FaveClick -> {
                launch {
                    when {
                        stateFlow.value.isAuthorized -> {
                            PendingActionStore.clear()
                            launch { FaveEventManager.push(FaveEvent.ReselectFave) }
                            MainTabsEventManager.push(MainEvent.OpenFave)
                        }
                        else -> {
                            PendingActionStore.set(PendingAction.OpenFave)
                            MainNavigator.forward(AuthDestination)
                        }
                    }
                }
            }
            is MainTabsIntent.SettingsClick -> {
                launch { SettingsEventManager.push(SettingsEvent.ReselectSettings) }
                launch { MainTabsEventManager.push(MainEvent.OpenSettings) }
            }
            is MainTabsIntent.AuthClick -> launch { MainNavigator.forward(AuthDestination) }
            is MainTabsIntent.AccountClick -> launch { MainNavigator.forward(AccountDestination) }
        }
    }

    override fun catch(throwable: Throwable) {
        when (throwable) {
            is CreateSessionException -> {
                launch { push(MainTabsEvent.ShowSnackbar(MoviesStrings.feed_auth_failure)) }
            }
            is AccountDetailsException -> {
                launch { push(MainTabsEvent.ShowSnackbar(MoviesStrings.feed_auth_failure)) }
            }
            else -> super.catch(throwable)
        }
    }
}
