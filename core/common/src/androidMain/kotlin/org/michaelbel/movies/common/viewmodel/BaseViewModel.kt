package org.michaelbel.movies.common.viewmodel

import androidx.annotation.CallSuper
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancelChildren
import org.michaelbel.movies.common.log.log
import kotlin.coroutines.CoroutineContext

open class BaseViewModel: ViewModel(), CoroutineScope {

    private val scopeJob: Job = SupervisorJob()

    private val errorHandler = CoroutineExceptionHandler { _, throwable ->
        handleError(throwable)
    }

    override val coroutineContext: CoroutineContext = scopeJob + Dispatchers.Main + errorHandler

    override fun onCleared() {
        coroutineContext.cancelChildren()
        super.onCleared()
    }

    @CallSuper
    protected open fun handleError(throwable: Throwable) {
        log(throwable)
    }
}