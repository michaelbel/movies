package org.michaelbel.movies.account

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.michaelbel.movies.common.exceptions.DeleteSessionException
import org.michaelbel.movies.common.viewmodel.BaseViewModel
import org.michaelbel.movies.interactor.Interactor
import org.michaelbel.movies.persistence.database.entity.AccountDb

@HiltViewModel
class AccountViewModel @Inject constructor(
    private val interactor: Interactor
): BaseViewModel() {

    var loading by mutableStateOf(false)

    val account: StateFlow<AccountDb?> = interactor.account
        .stateIn(
            scope = this,
            started = SharingStarted.Lazily,
            initialValue = AccountDb.Empty
        )

    override fun handleError(throwable: Throwable) {
        when (throwable) {
            is DeleteSessionException -> loading = false
            else -> super.handleError(throwable)
        }
    }

    fun onLogoutClick(onResult: () -> Unit) = launch {
        loading = true

        interactor.deleteSession()
        onResult()
    }
}