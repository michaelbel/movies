package org.michaelbel.moviemade.presentation.features.trailers

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import org.michaelbel.domain.TrailersRepository

@Suppress("unchecked_cast")
class TrailersFactory(private val repository: TrailersRepository): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return TrailersModel(repository) as T
    }
}