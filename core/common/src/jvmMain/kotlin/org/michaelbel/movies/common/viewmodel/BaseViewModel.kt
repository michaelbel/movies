package org.michaelbel.movies.common.viewmodel

import androidx.annotation.CallSuper
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import org.michaelbel.movies.common.dispatchers.uiDispatcher
import org.michaelbel.movies.common.log.log

open class BaseViewModel: ViewModel(
    viewModelScope = CoroutineScope(uiDispatcher + SupervisorJob())
) {

    @CallSuper
    protected open fun handleError(throwable: Throwable) {
        log(throwable)
    }
}