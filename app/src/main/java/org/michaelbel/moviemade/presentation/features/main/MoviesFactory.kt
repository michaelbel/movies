package org.michaelbel.moviemade.presentation.features.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import org.michaelbel.domain.MoviesRepository

@Suppress("unchecked_cast")
class MoviesFactory(private val repository: MoviesRepository): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MoviesModel(repository) as T
    }
}