package org.michaelbel.moviemade.presentation.features.reviews

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import org.michaelbel.domain.ReviewsRepository

@Suppress("unchecked_cast")
class ReviewsFactory(private val repository: ReviewsRepository): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ReviewsModel(repository) as T
    }
}