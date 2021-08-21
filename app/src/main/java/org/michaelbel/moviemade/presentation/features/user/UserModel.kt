package org.michaelbel.moviemade.presentation.features.user

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.michaelbel.data.remote.model.Account
import org.michaelbel.data.remote.model.SessionId
import org.michaelbel.domain.UsersRepository
import org.michaelbel.moviemade.BuildConfig.TMDB_API_KEY
import org.michaelbel.moviemade.core.state.ErrorState
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class UserModel @Inject constructor(val repository: UsersRepository): ViewModel() {

    var sessionDeleted = MutableSharedFlow<Boolean>()
    var error = MutableSharedFlow<Int>()
    var throwable = MutableSharedFlow<Throwable>()
    var account = MutableSharedFlow<Account>()

    fun deleteSession(sessionId: String) {
        viewModelScope.launch {
            try {
                val result = repository.deleteSession(TMDB_API_KEY, SessionId(sessionId))
                withContext(Dispatchers.Main) {
                    if (result.isSuccessful) {
                        val success = result.body()
                        if (success != null) {
                            sessionDeleted.emit(success.success)
                        }
                    } else {
                        error.emit(ErrorState.ERR_CANNOT_DELETE_SESSION)
                    }
                }
            } catch (e: Throwable) {
                error.emit(ErrorState.ERR_NO_CONNECTION)
            }
        }
    }

    fun accountDetails(sessionId: String) {
        viewModelScope.launch {
            val result: Response<Account> = repository.accountDetails(TMDB_API_KEY, sessionId)
            withContext(Dispatchers.Main) {
                if (result.isSuccessful) {
                    account.emit(result.body()!!)
                } else {
                    error.emit(result.code())
                }
            }
        }
    }
}