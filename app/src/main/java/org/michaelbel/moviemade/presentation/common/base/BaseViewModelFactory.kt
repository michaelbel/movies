package org.michaelbel.moviemade.presentation.common.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

@Suppress("unchecked_cast")
class BaseViewModelFactory<T>(val creator: () -> T): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return creator() as T
    }
}