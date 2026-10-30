package org.michaelbel.movies.account

import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import org.michaelbel.movies.account.intent.AccountIntent
import org.michaelbel.movies.account.model.AccountModel
import org.michaelbel.movies.common.mvi.Event
import org.michaelbel.movies.common.mvi.MoviesViewModel
import org.michaelbel.movies.domain.usecase.AccountPojoFlowUseCase
import org.michaelbel.movies.domain.usecase.DeleteSessionUseCase
import org.michaelbel.movies.domain.usecase.DeleteSessionUseCase.DeleteSessionException
import org.michaelbel.movies.domain.usecase.ProviderFlowUseCase
import org.michaelbel.movies.domain.usecase.TraktAccountRefreshUseCase
import org.michaelbel.movies.domain.usecase.TraktAccountRefreshUseCase.TraktAccountRefreshException
import org.michaelbel.movies.domain.usecase.TraktSignOutUseCase
import org.michaelbel.movies.interactor.entity.AppProvider
import org.michaelbel.movies.ui.navigation.MainNavigator

class AccountViewModel(
    private val accountPojoFlowUseCase: AccountPojoFlowUseCase,
    private val deleteSessionUseCase: DeleteSessionUseCase,
    private val providerFlowUseCase: ProviderFlowUseCase,
    private val traktSignOutUseCase: TraktSignOutUseCase,
    private val traktAccountRefreshUseCase: TraktAccountRefreshUseCase
): MoviesViewModel<AccountModel, AccountIntent, Event>(AccountModel()) {

    init {
        dispatch(AccountIntent.CollectAccountPojo)
        dispatch(AccountIntent.RefreshTraktAccount)
    }

    override fun dispatch(intent: AccountIntent) {
        when (intent) {
            is AccountIntent.CollectAccountPojo -> {
                launch {
                    accountPojoFlowUseCase(Unit).collectLatest { pojo ->
                        reduce { it.copy(accountPojo = pojo) }
                    }
                }
            }
            is AccountIntent.BackClick -> launch { MainNavigator.back() }
            is AccountIntent.LogoutClick -> {
                val job = launch {
                    val provider = AppProvider.transform(providerFlowUseCase(Unit).first())
                    when (provider) {
                        is AppProvider.Tmdb -> deleteSessionUseCase(Unit).getOrThrow()
                        is AppProvider.Trakt -> traktSignOutUseCase(Unit).getOrThrow()
                    }
                    MainNavigator.back()
                }
                reduce { it.copy(logoutJob = job) }
            }
            is AccountIntent.RefreshTraktAccount -> {
                launch {
                    val provider = AppProvider.transform(providerFlowUseCase(Unit).first())
                    if (provider is AppProvider.Trakt) {
                        traktAccountRefreshUseCase(Unit).getOrThrow()
                    }
                }
            }
        }
    }

    override fun catch(throwable: Throwable) {
        when (throwable) {
            is DeleteSessionException -> reduce { it.copy(logoutJob = null) }
            is TraktAccountRefreshException -> Unit
            else -> super.catch(throwable)
        }
    }
}
