package org.michaelbel.moviemade.presentation.features.user

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.michaelbel.data.remote.model.Account
import org.michaelbel.data.remote.model.SessionId
import org.michaelbel.domain.UsersRepository
import org.michaelbel.domain.live.LiveDataEvent
import org.michaelbel.moviemade.BuildConfig.TMDB_API_KEY
import org.michaelbel.moviemade.core.state.ErrorState
import retrofit2.Response

class UserModel(val repository: UsersRepository): ViewModel() {

    var sessionDeleted = MutableLiveData<LiveDataEvent<Boolean>>()
    var error = MutableLiveData<Int>()
    var throwable = MutableLiveData<Throwable>()
    var account = MutableLiveData<Account>()

    fun deleteSession(sessionId: String) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val result = repository.deleteSession(TMDB_API_KEY, SessionId(sessionId))
                withContext(Dispatchers.Main) {
                    if (result.isSuccessful) {
                        val success = result.body()
                        if (success != null) {
                            sessionDeleted.postValue(LiveDataEvent(success.success))
                        }
                    } else {
                        error.postValue(ErrorState.ERR_CANNOT_DELETE_SESSION)
                    }
                }
            } catch (e: Throwable) {
                error.postValue(ErrorState.ERR_NO_CONNECTION)
            }
        }
    }

    fun accountDetails(sessionId: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val result: Response<Account> = repository.accountDetails(TMDB_API_KEY, sessionId)
            withContext(Dispatchers.Main) {
                if (result.isSuccessful) {
                    account.postValue(result.body())
                } else {
                    error.postValue(result.code())
                }
            }
        }
    }
}