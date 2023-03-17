package org.michaelbel.movies.auth

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.michaelbel.movies.common.viewmodel.BaseViewModel
import org.michaelbel.movies.domain.data.entity.AccountDb
import org.michaelbel.movies.domain.interactor.account.AccountInteractor
import org.michaelbel.movies.domain.interactor.authentication.AuthenticationInteractor
import javax.inject.Inject

@HiltViewModel
class AccountViewModel @Inject constructor(
    private val authenticationInteractor: AuthenticationInteractor,
    accountInteractor: AccountInteractor,
): BaseViewModel() {

    private val _loading: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading.asStateFlow()

    val account: StateFlow<AccountDb?> = accountInteractor.account
        .stateIn(
            scope = this,
            started = SharingStarted.Lazily,
            initialValue = AccountDb.Empty
        )

    override fun handleError(throwable: Throwable) {
        _loading.value = false
        super.handleError(throwable)
    }

    fun onLogoutClick(onResult: () -> Unit) = launch {
        _loading.value = true

        delay(5000)
        authenticationInteractor.deleteSession()
        onResult()
    }
}