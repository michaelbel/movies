package org.michaelbel.movies.common.viewmodel

import androidx.annotation.CallSuper
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.swing.Swing
import org.michaelbel.movies.common.log.log

open class BaseViewModel: ViewModel(
    viewModelScope = CoroutineScope(Dispatchers.Swing + SupervisorJob())
) {

    @CallSuper
    protected open fun handleError(throwable: Throwable) {
        log(throwable)
    }
}