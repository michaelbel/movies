@file:Suppress("unused", "unchecked_cast")

package org.michaelbel.moviemade.ktx

import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

//region Assisted ViewModel

inline fun <reified T: ViewModel> Fragment.assistedViewModels(
    crossinline viewModelProducer: () -> T
): Lazy<T> = viewModels {
    object: ViewModelProvider.Factory {
        override fun <T: ViewModel> create(modelClass: Class<T>): T {
            return viewModelProducer() as T
        }
    }
}

inline fun <reified T: ViewModel> Fragment.assistedActivityViewModels(
    crossinline viewModelProducer: () -> T
): Lazy<T> = activityViewModels {
    object: ViewModelProvider.Factory {
        override fun <T: ViewModel> create(modelClass: Class<T>): T {
            return viewModelProducer() as T
        }
    }
}

inline fun <reified T: ViewModel> FragmentActivity.assistedViewModels(
    crossinline viewModelProducer: () -> T
): Lazy<T> = viewModels {
    object: ViewModelProvider.Factory {
        override fun <T: ViewModel> create(modelClass: Class<T>): T {
            return viewModelProducer() as T
        }
    }
}

//endregion

//region SavedState ViewModel

inline fun <reified T: ViewModel> Fragment.savedStateViewModels(
    crossinline viewModelProducer: (SavedStateHandle) -> T
): Lazy<T> = viewModels {
    object: AbstractSavedStateViewModelFactory(this, arguments) {
        override fun <T: ViewModel> create(
            key: String,
            modelClass: Class<T>,
            handle: SavedStateHandle
        ): T {
            return viewModelProducer(handle) as T
        }
    }
}

inline fun <reified T: ViewModel> Fragment.savedStateActivityViewModels(
    crossinline viewModelProducer: (SavedStateHandle) -> T
): Lazy<T> = activityViewModels {
    object: AbstractSavedStateViewModelFactory(this, arguments) {
        override fun <T: ViewModel> create(
            key: String,
            modelClass: Class<T>,
            handle: SavedStateHandle
        ): T {
            return viewModelProducer(handle) as T
        }
    }
}

inline fun <reified T: ViewModel> FragmentActivity.savedStateViewModels(
    crossinline viewModelProducer: (SavedStateHandle) -> T
): Lazy<T> = viewModels {
    object: AbstractSavedStateViewModelFactory(this, intent.extras) {
        override fun <T: ViewModel> create(
            key: String,
            modelClass: Class<T>,
            handle: SavedStateHandle
        ): T {
            return viewModelProducer(handle) as T
        }
    }
}

//endregion