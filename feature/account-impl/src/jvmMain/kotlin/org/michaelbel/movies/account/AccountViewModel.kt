package org.michaelbel.movies.account

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.michaelbel.movies.common.exceptions.DeleteSessionException
import org.michaelbel.movies.common.viewmodel.BaseViewModel
import org.michaelbel.movies.interactor.Interactor
import org.michaelbel.movies.persistence.database.entity.pojo.AccountPojo

class AccountViewModel(
    private val interactor: Interactor
): BaseViewModel() {

    var loading by mutableStateOf(false)

    val account: StateFlow<AccountPojo?> = interactor.account
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Lazily,
            initialValue = AccountPojo.Empty
        )

    override fun handleError(throwable: Throwable) {
        when (throwable) {
            is DeleteSessionException -> loading = false
            else -> super.handleError(throwable)
        }
    }

    fun onLogoutClick(onResult: () -> Unit) = viewModelScope.launch {
        loading = true

        interactor.deleteSession()
        onResult()
    }
}