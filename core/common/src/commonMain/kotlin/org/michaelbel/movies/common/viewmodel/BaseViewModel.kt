package org.michaelbel.movies.common.viewmodel

import androidx.annotation.CallSuper
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import org.michaelbel.movies.common.dispatchers.uiDispatcher
import org.michaelbel.movies.common.log.log

open class BaseViewModel: ViewModel(
    viewModelScope = CoroutineScope(uiDispatcher + SupervisorJob())
) {

    private val errorHandler = CoroutineExceptionHandler { _, throwable ->
        handleError(throwable)
    }

    val scope: CoroutineScope
        get() = CoroutineScope(viewModelScope.coroutineContext + errorHandler)

    @CallSuper
    protected open fun handleError(throwable: Throwable) {
        log(throwable)
    }
}