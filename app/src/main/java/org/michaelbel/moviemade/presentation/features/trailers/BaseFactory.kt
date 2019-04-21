package org.michaelbel.moviemade.presentation.features.trailers

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

@Suppress("unchecked_cast")
class BaseFactory<T>(private val creator: () -> T): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return creator as T
    }
}