@file:Suppress("UNCHECKED_CAST")

package org.michaelbel.moviemade.app.ktx

import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel

inline fun <reified T: ViewModel> Fragment.savedStateViewModels(
    crossinline viewModelProducer: (SavedStateHandle) -> T
): Lazy<T> = viewModels {
    object: AbstractSavedStateViewModelFactory(this, arguments) {
        override fun <T: ViewModel> create(
            key: String,
            modelClass: Class<T>,
            handle: SavedStateHandle
        ): T = viewModelProducer(handle) as T
    }
}