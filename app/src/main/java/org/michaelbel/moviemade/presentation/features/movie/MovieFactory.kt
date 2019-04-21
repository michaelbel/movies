package org.michaelbel.moviemade.presentation.features.movie

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import org.michaelbel.domain.MoviesRepository

@Suppress("unchecked_cast")
class MovieFactory(private val repository: MoviesRepository): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MovieModel(repository) as T
    }
}